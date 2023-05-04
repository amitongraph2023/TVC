package com.tokens.service;

import com.tokens.models.User;

public interface UserService {

	public boolean addOrUpdateMasterKey(int userId, String masterKey);

	public void registerAdminOrUser(User user);
	
	 public User findUserByUserName(String username);
	
}
