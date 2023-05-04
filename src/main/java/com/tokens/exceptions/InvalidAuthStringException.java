package com.tokens.exceptions;

public class InvalidAuthStringException extends Exception{

	private static final long serialVersionUID = 1L;
	private String exceptionMessage = "The Auth String is found invalid";

	@Override
	public String getMessage() {
		return exceptionMessage;
	}
}
