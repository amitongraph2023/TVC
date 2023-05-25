package com.tokens.service;

import java.util.List;

import com.tokens.models.MasterKeyLogs;
import com.tokens.models.User;

public interface UserService {

	public boolean addOrUpdateMasterKey(int userId, String masterKey);

	public void registerAdminOrUser(User user) throws Exception;

	public User findUserByUserName(String username);

	public List<MasterKeyLogs> getAllMasterKeyLogs(int userId);
	
	public boolean changeAdminPassword(int userId, String oldPassword,String newPassword);
	
	public boolean validateAdminPasswords(int userId, String admin1Password, String admin2Password);

}
