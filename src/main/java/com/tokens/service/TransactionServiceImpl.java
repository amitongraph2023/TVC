package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tokens.models.MasterKey;
import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatus;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.models.User;
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
	TrasactionStatusLogsRepository transactionStatusLogsRepository;
	
	@Override
	public CloudResponse generateTransactionToken(CloudRequest request) {
		CloudResponse response = null;
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
			Transaction transaction = saveTransaction(request, token);
			// Transaction_ID
			response = new CloudResponse(token, transaction.getTransactionId());
		} catch (Exception e) {
			logger.error("Exception occurred while generation Token");
		}
		return response;
	}

	@Transactional
	public Transaction saveTransaction(CloudRequest req, String token) {

		Transaction transaction = null;

		try {

			transaction = new Transaction(token, Integer.parseInt(req.getCustomerId()),
					Double.parseDouble(req.getAmount()), req.getCreatedDate(), Integer.parseInt(req.getLocationId()),
					Integer.parseInt(req.getPosId()), req.getCardNumber(), req.getSourceIp(), req.getGpsLocation());

			transactionRepository.save(transaction);

			// need to save data in location and pos table here

		} catch (Exception e) {
			logger.error("Exception occurred while saving Transaction");
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

	@Override
	public Boolean updateTransactionStatus(Integer transactionId, String status) {

		boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		
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
				
				saveTransactionStatusLogs(transactionId, transaction.getStatus().toString(), transaction.getLastUpdated());
				
				isUpdated = true;
				
			}
		} catch (Exception e) {
			logger.error("Transaction not found for ID: " + transactionId);
			
		}
		return isUpdated;
		
	}
	

	@Override
	public TransactionStatusLogs saveTransactionStatusLogs(Integer transactionId, String status, String lastUpdated) {	
		TransactionStatusLogs transactionStatusLogs = null;
		
		try {
			 transactionStatusLogs = new TransactionStatusLogs(transactionId, status, lastUpdated);
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
