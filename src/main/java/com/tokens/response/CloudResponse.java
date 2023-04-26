package com.tokens.response;

public class CloudResponse {

	private String generatedToken;

	private Integer transactionId;

	private String message;

	public CloudResponse() { }

	public CloudResponse(String generatedToken, Integer transactionId) {
		super();
		this.generatedToken = generatedToken;
		this.transactionId = transactionId;
	}

	public CloudResponse(String generatedToken, Integer transactionId, String message) {
		super();
		this.generatedToken = generatedToken;
		this.transactionId = transactionId;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
