package com.tokens.response;


public class AuthResponse {

	private String jwtToken;
	
	public AuthResponse( String jwtToken) {
        this.jwtToken = jwtToken;
    }

	public AuthResponse(){

	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	
}
