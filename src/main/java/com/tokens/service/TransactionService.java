package com.tokens.service;


import java.util.List;

import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;

public interface TransactionService {

	CloudResponse generateTransactionToken(CloudRequest request);
	
	Transaction saveTransaction(CloudRequest req, String token);
	
	int countAllTransaction();
	
<<<<<<< HEAD
	String validateCloudRequest(CloudRequest req);

=======
	Boolean updateTransactionStatus(Integer transactionId, String status);
	
	TransactionStatusLogs saveTransactionStatusLogs(Integer transactionId, String status, String lastUpdated);
	
	List<TransactionStatusLogs> logsUpdatedTransactionStatus();
	
	Transaction getSuccessTransactions();
	
	Transaction getFailedTransactions();
	
	List<Transaction> logsTransactionToken();
	
>>>>>>> a83f7a606f990e7278cb1ce162e13defc4d9acd1
}

