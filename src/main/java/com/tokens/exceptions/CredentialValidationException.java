package com.tokens.exceptions;

public class CredentialValidationException extends Exception{

	private String exceptionMessage;

	public CredentialValidationException(String message) {
		this.exceptionMessage = message;
	}

	@Override
	public String getMessage() {
		return this.exceptionMessage;
	}
}
