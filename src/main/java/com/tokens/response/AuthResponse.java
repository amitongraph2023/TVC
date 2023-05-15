package com.tokens.response;

import com.tokens.models.User;

public class AuthResponse {

	private String role;
	
	private String jwtToken;
	

	public AuthResponse(String role, String jwtToken) {
		super();
		this.role = role;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	
}
