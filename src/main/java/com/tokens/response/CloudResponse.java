package com.tokens.response;

public class CloudResponse {

	private String generatedToken;

	private Integer transactionId;

	public CloudResponse() {
	}
	
	public CloudResponse(String generatedToken, Integer transactionId) {
		super();
		this.generatedToken = generatedToken;
		this.transactionId = transactionId;
	}

	public String getGeneratedToken() {
		return generatedToken;
	}

	public void setGeneratedToken(String generatedToken) {
		this.generatedToken = generatedToken;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

}
