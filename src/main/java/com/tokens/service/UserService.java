package com.tokens.service;

public interface UserService {

	public boolean addOrUpdateAdminMasterKey(int userId, String masterKey);

	public boolean addUserMasterKey(int userId, String masterKey);

	public void registerAdminOrUser(String userName, String password, String role);
	
	
}
