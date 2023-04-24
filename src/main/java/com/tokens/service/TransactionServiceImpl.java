package com.tokens.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import com.tokens.models.User;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.repository.TransactionRepository;
import com.tokens.repository.UserRepository;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;
import com.tokens.utils.TokenGenerator;

@Component
@Repository
@Controller
@Service
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	MasterKeyRepository masterKeyRepository;

	@Override
	public CloudResponse generateTransactionToken(CloudRequest request) {
		CloudResponse response = null;
		try {
			String token = "";
			User user = userRepository.findById(Integer.parseInt(request.getUserId())).get();
			if (user != null) {
				MasterKey key = masterKeyRepository.findAll().get(0);
				if (key != null && key.getMasterKey() != null) {
					tokenGenerator.generateToken(key.getMasterKey(), user.getUserId().toString());
				} else {
					logger.error("cannot generate Token Without MasterKey");
					throw new Exception("cannot generate Token Without MasterKey");
				}
			} else {
				logger.error("User not found");
				throw new Exception("User not found");
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
			transaction = new Transaction(token, Integer.parseInt(req.getUserId()), req.getAmount(),
					req.getCreatedDate(), req.getLocationId(), req.getPosId(), req.getCardNumber(), req.getSourceIp(),
					req.getGpsLocation());

			transactionRepository.save(transaction);

		} catch (Exception e) {
			logger.error("Exception occurred while saving Transaction");
		}

		return transaction;
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

		try {

			Transaction transaction = transactionRepository.findByTransactionId(transactionId);
			if (transaction != null) {

				transaction.setStatus(status);
				transaction.setLastUpdated(dateFormat.format(new Date()));
				transactionRepository.save(transaction);

				isUpdated = true;
			}
		} catch (Exception e) {
			logger.error("Transaction not found for ID: " + transactionId);
		}
		return isUpdated;
	}

	@Override
	public List<Transaction> logsUpdatedTransactionStatus() {
		List<Transaction> transaction = transactionRepository.findAllTransactions(TransactionStatus.NONE.name());
		return transaction;
	}
	
}
