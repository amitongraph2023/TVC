package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokens.models.Location;
import com.tokens.models.MasterKey;
import com.tokens.models.Pos;
import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatus;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.models.User;
import com.tokens.repository.LocationRepository;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.repository.PosRepository;
import com.tokens.repository.TransactionRepository;
import com.tokens.repository.TrasactionStatusLogsRepository;
import com.tokens.repository.UserRepository;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;
import com.tokens.utils.CodeGenerator;
import com.tokens.utils.JwtUtil;

@Service
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	TvsCsvReader reader;

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

	@Autowired
	PosRepository posRepository;
	
	@Override
	public CloudResponse generateTransactionToken(CloudRequest request) {
		CloudResponse response = null;
		Transaction transaction = null;
		try {
			String token = "";

			if (request.getStationId() != null) {
				int merchantId = Integer.parseInt(request.getStationId());
				Location location = locationRepository.findByMerchantId(merchantId);
				if (location == null) {
					return new CloudResponse("", "", "MerchantId doesn't Exists");
				}
			}

			MasterKey key = masterKeyRepository.findMasterKeyBySystemId(request.getSystemId());
			if (key != null && key.getMasterKey() != null) {
				token = CodeGenerator.generateHashCode(key.getMasterKey());
			} else {
				logger.error("Cannot generate Token Without MasterKey");
				return new CloudResponse("", "", "SystemId doesn't Exists");
			}

			// saving transactions in DB
			transaction = saveTransaction(request, token);

			if (transaction != null) {
				response = new CloudResponse(token, transaction.getTransactionId(), "");
			} else {
				return new CloudResponse("", " ", "Exception Occurred while saving Transaction");
			}
		} catch (Exception e) {
			logger.error("Exception occurred while generation Token, Error : " + e.getMessage());
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

				transaction = new Transaction(req.getTransactionId(), token, req.getCustomerId(),
						Double.parseDouble(req.getAmount()), req.getCreatedDate(), Integer.parseInt(req.getStationId()),
						Integer.parseInt(req.getPosId()), req.getCardNumber(), req.getSourceIp(), req.getGpsLocation(),
						TransactionStatus.PENDING.name(), req.getSystemId());

				if (checkLocationIfExsists(transaction.getMerchantId())) {
					transaction = transactionRepository.save(transaction);
					savePos(transaction.getMerchantId(), transaction.getPosId());

					return transaction;
				} else {
					throw new Exception("Location doesn't exist");
				}

			} catch (Exception ex) {
				logger.error("Exception occurred while saving Transaction, Error :" + ex.getMessage());
				return null;
			}

		} else {
			logger.error("Cloud validation failed, Error :" + exceptionMessage);
		}
		return null;
	}

	@Override
	public List<Transaction> logsTransactionToken(int userId) {
		User user = userRepository.findById(userId).get();
		List<Transaction> transactionTokenLog = transactionRepository.findTransactionLogs(user.getSystemId());
		return transactionTokenLog;
	}

	public String validateCloudRequest(CloudRequest req) {
		String exceptionMessage = "";

		if (req.getTransactionId() == null) {
			exceptionMessage = "TransactionId is null";
			return exceptionMessage;
		} else if (req.getAmount() == null || Double.parseDouble(req.getAmount()) < 0) {

			exceptionMessage = "Amount is null or less than 0";
			return exceptionMessage;
		} else if (req.getCardNumber() == null) {

			exceptionMessage = "CardNumber is null";
			return exceptionMessage;
		} else if (req.getCustomerId() == null) {

			exceptionMessage = "CustomerId is null";
			return exceptionMessage;
		} else if (req.getStationId() == null) {

			exceptionMessage = "StationId is null";
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

	public void savePos(int merchantId, int posID) {
		Pos pos = new Pos(posID, merchantId);
		posRepository.save(pos);
	}


	public Boolean checkLocationIfExsists(Integer merchantId) {

		Location location = locationRepository.findByMerchantId(merchantId);
		if (location == null) {
			logger.info("location doesn't exist");
			return false;
		}
		return true;
	}

	@Transactional
	public String updateTransactionStatus(String transactionId, String status) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        String response = "";

		try {
			if (transaction != null) {
				if (status.equalsIgnoreCase("Success")) {
					transaction.setStatus(TransactionStatus.COMPLETED.name());
				} else if (status.equalsIgnoreCase("FAILED")) {
					if (!transaction.getStatus().equalsIgnoreCase("COMPLETED")) {
						transaction.setStatus(TransactionStatus.FAILED.name());
					} else {
						response = "Transaction Status Already Completed";
						return response;
					}
					
				} else {
					response ="invalid status, status Should be SUCCESS OR FAILED";
					return response;
				}
				transaction.setLastUpdated(dateFormat.format(new Date()));
				if (!transaction.getStatus().equalsIgnoreCase("failed")) {
					transactionRepository.save(transaction);
				}
				saveTransactionStatusLogs(transactionId, transaction.getStatus(), transaction.getLastUpdated(),
						transaction.getSystemId());
				
				response = "success";
			}
		} catch (Exception e) {
			logger.error("Transaction not found for ID: " + transactionId);
		}
		return response;
	}

	@Override
	@Transactional
	public TransactionStatusLogs saveTransactionStatusLogs(String transactionId, String status, String lastUpdated,
			String systemId) {
		TransactionStatusLogs transactionStatusLogs = null;

		try {
			transactionStatusLogs = new TransactionStatusLogs(transactionId, status, lastUpdated, systemId);
			transactionStatusLogsRepository.save(transactionStatusLogs);

		} catch (Exception ex) {
			logger.error("Exception occurred while saving TransactionStatusLogs, Error :" + ex.getMessage());
		}
		return transactionStatusLogs;
	}

	@Override
	public List<TransactionStatusLogs> getTransactionStatusLogs(int userId) {
		User user = userRepository.findById(userId).get();
		List<TransactionStatusLogs> transactionLog = transactionStatusLogsRepository
				.findTransactionStatusLogs(user.getSystemId());
		return transactionLog;
	}

	@Override
	public List<Location> getTopLocations(String username) {
		User user = userRepository.findByUserName(username);
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationRepository.findTopLocations(user.getSystemId());
		} catch (Exception ex) {
			logger.error("Exception while getting Top Locations: " + ex.getMessage());
		}
		return locationList;
	}

	@Override
	public int countAllTransactionofSystem(String username) {
		int count = 0;
		try {
			User user = userRepository.findByUserName(username);
			count = (int) transactionRepository.findTransactionCountofSystem(user.getSystemId());
		} catch (Exception ex) {
			logger.error("Exception while counting transaction, Error : " + ex.getMessage());
		}
		return count;
	}

	public void saveLocationFromCSV() {
		List<Location> locationList = reader.saveLocations();
		locationRepository.saveAll(locationList);
	}
}
