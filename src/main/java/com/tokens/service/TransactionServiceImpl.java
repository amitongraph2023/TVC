package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokens.models.Location;
import com.tokens.models.MasterKey;
import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatus;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.models.User;
import com.tokens.repository.LocationRepository;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.repository.TransactionRepository;
import com.tokens.repository.TrasactionStatusLogsRepository;
import com.tokens.repository.UserRepository;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;
import com.tokens.utils.JwtUtil;

@Service
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil tokenGenerator;

	@Autowired
	MasterKeyRepository masterKeyRepository;

	@Autowired
	LocationRepository locationRepository;
 
  @Autowired
	TrasactionStatusLogsRepository transactionStatusLogsRepository;
	
	@Override
	public CloudResponse generateTransactionToken(CloudRequest request) {
		CloudResponse response = null;
		Transaction transaction = null;
		try {
			String token = "";

			MasterKey key = masterKeyRepository.findAll().get(0);
			if (key != null && key.getMasterKey() != null) {
				tokenGenerator.generateCloudToken(key.getMasterKey(), request.getCustomerId().toString());
			} else {
				logger.error("cannot generate Token Without MasterKey");
				throw new Exception("cannot generate Token Without MasterKey");
			}

			// saving transactions in DB
			transaction = saveTransaction(request, token);

			if (transaction != null) {
				response = new CloudResponse(token, transaction.getTransactionId());
			} else {
				return new CloudResponse();
			}
		} catch (Exception e) {
			logger.error("Exception occurred while generation Token");
		}
		return response;
	}

	@Transactional
	public Transaction saveTransaction(CloudRequest req, String token) {

		Transaction transaction = null;
		String exceptionMessage = validateCloudRequest(req);
		logger.info("exceptionMessage : " + exceptionMessage);
		if (exceptionMessage.length() == 0) {
			try {

				transaction = new Transaction(token, Integer.parseInt(req.getCustomerId()),
						Double.parseDouble(req.getAmount()), req.getCreatedDate(),
						Integer.parseInt(req.getMerchantId()), req.getMerchantName(), Integer.parseInt(req.getPosId()),
						req.getCardNumber(), req.getSourceIp(), req.getGpsLocation());

//				if(checkLocationIfExsists(transaction.getMerchantId(),transaction.getMerchantName())){
//				    transactionRepository.save(transaction);
//				}else {
//					throw new Exception("Location Does Not Esists");
//				}
			
				// need to save data in location and pos table here - not sure for it
				// as we will be having merchant details in our db to validate req with merchant Id and name
				// so we need it before in order to valdiate and no need to save location then
				//saveLocation(transaction);

			} catch (Exception e) {
				logger.error("Exception occurred while saving Transaction");
			}

		} else {
			logger.error("Invalidate Request, Error :" + exceptionMessage);
		}
		return transaction;
	}

	@Override
	public List<Transaction> logsTransactionToken() {
		List<Transaction> transactionTokenLog = transactionRepository.findAll();
		return transactionTokenLog;
	}
	
	@Override
	public int countAllTransaction() {
		int count = (int) transactionRepository.count();
		return count;
	}

	public String validateCloudRequest(CloudRequest req) {
		String exceptionMessage = "";

		if (req.getTransactionId() == null) {
			exceptionMessage = "TransactionId is null";
			return exceptionMessage;
		} else if (req.getAmount() == null || Integer.parseInt(req.getAmount()) < 0) {

			exceptionMessage = "Amount is null or less than 0";
			return exceptionMessage;
		} else if (req.getCardNumber() == null) {

			exceptionMessage = "CardNumber is null";
			return exceptionMessage;
		} else if (req.getCustomerId() == null) {

			exceptionMessage = "CustomerId is null";
			return exceptionMessage;
		} else if (req.getMerchantId() == null) {

			exceptionMessage = "MerchantId is null";
			return exceptionMessage;
		} else if (req.getMerchantName() == null) {

			exceptionMessage = "MerchantName is null";
			return exceptionMessage;
		} else if (req.getPosId() == null) {

			exceptionMessage = "PosId is null";
			return exceptionMessage;
		} else if (req.getSourceIp() == null) {

			exceptionMessage = "SourceIp is null";
			return exceptionMessage;
		}
		return exceptionMessage;
	}

	public void saveLocation(Transaction transaction) {

	}

	public Boolean checkLocationIfExsists(Integer merchantId, String merchantName) {

		Location location = locationRepository.findLocationByMerchantId(merchantId).get();
		if (location == null) {
			logger.info("location does not esists");
			return false;
		}else if(!location.getMerchantName().equalsIgnoreCase(merchantName)) {
			logger.info("location does not esists");
			return false;
		}

		return true;

	}

	@Override
	public Boolean updateTransactionStatus(Integer internalTransactionId, Integer transactionId, String status) {

		boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		Transaction transaction = transactionRepository.findByInternalTransactionId(internalTransactionId);
		
		if (transaction.getTransactionId() == null) {
			transaction.setStatus(TransactionStatus.FAILED);
		}
		
		try {

			if (transaction != null) {

				if (status.equals("SUCCESS")) {
					transaction.setStatus(TransactionStatus.COMPLETED);

				} else if ((status.equals("") || status == null) && transaction.getStatus() != TransactionStatus.COMPLETED) {
					transaction.setStatus(TransactionStatus.PENDING);					
				} 
				
				transaction.setLastUpdated(dateFormat.format(new Date()));
				transactionRepository.save(transaction);
				
				saveTransactionStatusLogs(internalTransactionId, transactionId, transaction.getStatus().toString(), transaction.getLastUpdated());
				
				isUpdated = true;
				
			}
		} catch (Exception e) {
			logger.error("Transaction not found for ID: " + transactionId);
			
		}
		return isUpdated;
		
	}
	

	@Override
	public TransactionStatusLogs saveTransactionStatusLogs(Integer internalTransactionId, Integer transactionId, String status, String lastUpdated) {	
		TransactionStatusLogs transactionStatusLogs = null;
		
		try {
			 transactionStatusLogs = new TransactionStatusLogs(internalTransactionId, transactionId, status, lastUpdated);
			 transactionStatusLogsRepository.save(transactionStatusLogs);
			
		} catch (Exception e) {
			logger.error("Exception occurred while saving TransactionStatusLogs");
		}
		
		return transactionStatusLogs;
		
	}
	
	@Override
	public List<TransactionStatusLogs> logsUpdatedTransactionStatus() {
		List<TransactionStatusLogs> transactionLog = transactionStatusLogsRepository.findAll();
		return transactionLog;
		
	}

	@Override
	public Transaction getSuccessTransactions() {
		Transaction transaction = transactionRepository.findByStatus(TransactionStatus.COMPLETED);
		return transaction;
	}

	@Override
	public Transaction getFailedTransactions() {
		Transaction transaction = transactionRepository.findByStatus(TransactionStatus.FAILED);
		return transaction;
	}
	
}
