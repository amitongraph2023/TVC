package com.tokens.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokens.models.Transaction;
import com.tokens.repository.TransactionRepository;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;
import com.tokens.utils.TokenGenerator;

@Component
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
			
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public CloudResponse generateTransactionToken(CloudRequest request) {
		CloudResponse response = null;
		try {
			String token = TokenGenerator.generateToken(request.getMasterKey());
			
			//saving transactions in DB
			saveTransaction(request, token);
			
			response = new CloudResponse(token, request.getMasterKey(), request.getUserId());
		} catch (Exception e) {
			logger.error("Exception occurred while generation Token");
		}
		return response;
	}

	public void saveTransaction(CloudRequest req, String token) {
		try {         

			Transaction transaction = new Transaction(token, req.getUserId(), req.getAmount(), req.getDate(), req.getLocationId(), req.getPosId(), req.getCardNumber(), req.getMasterKey(), req.getGpsLocation());
			transactionRepository.save(transaction);
		} catch (Exception e) {
			logger.error("Exception occurred while saving Transaction");
		}
	}

	@Override
	public int countAllTransaction() {
		int count = (int) transactionRepository.count();
		return count;
	}

}
