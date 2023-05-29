package com.tokens.request;

public class ValidatePasswordRequest {

	private Integer userId;
	
	private String admin1Password;
	
	private String admin2Password;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAdmin1Password() {
		return admin1Password;
	}

	public void setAdmin1Password(String admin1Password) {
		this.admin1Password = admin1Password;
	}

	public String getAdmin2Password() {
		return admin2Password;
	}

	public void setAdmin2Password(String admin2Password) {
		this.admin2Password = admin2Password;
	}

}
