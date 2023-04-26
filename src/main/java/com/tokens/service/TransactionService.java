package com.tokens.service;

import java.util.List;

import com.tokens.models.Transaction;
import com.tokens.request.CloudRequest;
import com.tokens.response.CloudResponse;

public interface TransactionService {

	CloudResponse generateTransactionToken(CloudRequest request);
	
	Transaction saveTransaction(CloudRequest req, String token);
	
	int countAllTransaction();
	
	String validateCloudRequest(CloudRequest req);

}
