package com.tokens.response;

public class CloudResponse {

	private String generatedToken;

	private String masterKey;

	private Integer userId;
	
	public CloudResponse() {}
	
	public CloudResponse(String generatedToken, String masterKey, Integer userId) {
		super();
		this.generatedToken = generatedToken;
		this.masterKey = masterKey;
		this.userId = userId;
	}

	public String getGeneratedToken() {
		return generatedToken;
	}

	public void setGeneratedToken(String generatedToken) {
		this.generatedToken = generatedToken;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
