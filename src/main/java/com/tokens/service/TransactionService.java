package com.tokens.service;


import java.util.List;

import com.tokens.models.Location;
import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;

public interface TransactionService {

	CloudResponse generateTransactionToken(CloudRequest request);
	
	Transaction saveTransaction(CloudRequest req, String token);
	
	int countAllTransactionofSystem(String username);
	
	String validateCloudRequest(CloudRequest req);

	Boolean updateTransactionStatus(Integer transactionId, String status);
	
	TransactionStatusLogs saveTransactionStatusLogs(Integer transactionId, String status, String lastUpdated);
	
	List<TransactionStatusLogs> getTransactionStatusLogs();
	
	List<Transaction> logsTransactionToken();
	
	List<Location> getTopLocations(String username);
	
}

