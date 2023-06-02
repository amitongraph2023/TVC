package com.tokens.service;

import java.util.List;

import com.tokens.models.Admin;
import com.tokens.models.MasterKeyLogs;
import com.tokens.models.User;

public interface UserService {

	public boolean addOrUpdateMasterKey(int userId, String masterKey);

	public void registerAdminOrUser(User user) throws Exception;

	public User findUserByUserName(String username);

	public List<MasterKeyLogs> getAllMasterKeyLogs(int userId);
	
	public boolean changeAdminPassword(int userId, String oldPassword,String newPassword, String confirmPassword);
	
	public boolean validateAdmin1Passwords(int userId, String adminPassword);
	
	public boolean validateAdmin2Passwords(int userId, String adminPassword);

	public void startStopServer(int userId, String status);
	
	public Admin InitiallizeAdmin(String admin1Password, String admin2Password, String systemId);
	
}
