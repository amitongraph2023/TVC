package com.tokens.request;

public class TransactionStatusRequest {

	private String transactionId;
	
	private String status;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String transactionStatus) {
		this.status = transactionStatus;
	}
		
}
