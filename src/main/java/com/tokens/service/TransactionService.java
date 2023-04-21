package com.tokens.service;

import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;

public interface TransactionService {

	CloudResponse generateTransactionToken(CloudRequest request);
	
	void saveTransaction(CloudRequest req, String token);
	
	int countAllTransaction();
}
