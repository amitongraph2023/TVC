package com.tokens.service;

public interface UserService {

	public boolean addOrUpdateMasterKey(int userId, String masterKey);

	public void registerAdminOrUser(String userName, String password, String role);
	
	
}
