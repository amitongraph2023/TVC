package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tokens.models.Admin;
import com.tokens.models.MasterKey;
import com.tokens.models.MasterKeyLogs;
import com.tokens.models.ServerStatus;
import com.tokens.models.User;
import com.tokens.repository.AdminRepository;
import com.tokens.repository.MasterKeyLogsRepository;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.repository.ServerStatusRepository;
import com.tokens.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	MasterKeyRepository masterKeyRepository;

	@Autowired
	MasterKeyLogsRepository masterKeyLogsRepository;

	@Autowired
	ServerStatusRepository serverStatusRepository;
	
	@Autowired(required = true)
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	AdminRepository adminRepository;
	
	@Override
	@Transactional
	public boolean addOrUpdateMasterKey(int userId, String masterKey) {
		boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		User user = userRepository.findById(userId).get();
		Admin admin = adminRepository.findByUserId(userId);
		MasterKey key = null;
		key = masterKeyRepository.findMasterKeyByUserId(userId);
		if (key != null && user.getRole().equals("Admin")) {
			key.setMasterKey(masterKey);
			key.setSystemId(admin.getSystemId());
			key.setLastUpdated(dateFormat.format(new Date()));
			masterKeyRepository.save(key);
			saveMasterKeyLogs(key);
			isUpdated = true;
		} else if (key == null && user.getRole().equals("User")) {
			key = new MasterKey();
			key.setMasterKey(masterKey);
			key.setUserId(userId);
			key.setCreatedOn(dateFormat.format(new Date()));
			key = masterKeyRepository.save(key);
			saveMasterKeyLogs(key);
			isUpdated = true;
		}else if (key == null && user.getRole().equals("Admin")) {
			key = new MasterKey();
			key.setMasterKey(masterKey);
			key.setUserId(userId);
			key.setCreatedOn(dateFormat.format(new Date()));
			key.setSystemId(admin.getSystemId());
			key = masterKeyRepository.save(key);
			saveMasterKeyLogs(key);
			isUpdated = true;
		}else if (user.getRole().equals("User")) {
			logger.error("User cannot update MasterKey");
			isUpdated = false;
		}

		return isUpdated;
	}

	@Override
	public void registerAdminOrUser(User user) throws Exception {
		if (userRepository.findByUserName(user.getUserName()) != null) {
			throw new Exception("Username is already taken");
		}
		user.setPassword(getEncodedPassword(user.getPassword()));
		userRepository.save(user);
	}

	public String getEncodedPassword(String password) {
		logger.info("getting password encoded");
		String pass = null;
		try {
			pass = passwordEncoder.encode(password);
		} catch (Exception ex) {
			logger.error("Exception occured while encoding password, Error : " + ex.getMessage());
		}
		return pass;
	}

	@Transactional
	public void saveMasterKeyLogs(MasterKey masterKey) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		MasterKeyLogs logs = new MasterKeyLogs();
		logs.setMasterKeyId(masterKey.getMasterKeyId());
		logs.setMasterKey(masterKey.getMasterKey());
		logs.setSystemId(masterKey.getSystemId());
		logs.setCreatedOn(dateFormat.format(new Date()));
		logs.setUserId(masterKey.getUserId());
		try {
			masterKeyLogsRepository.save(logs);			
		} catch (Exception e) {
			logger.error("Exception occurred while saving masterKeyLogs in DB, Error : " + e.getMessage());
		}
		

	}

	public List<MasterKeyLogs> getAllMasterKeyLogs(int userId) {
		List<MasterKeyLogs> list = masterKeyLogsRepository.findMasterKeyLogs(userId);
		return list;
	}

	@Override
	public User findUserByUserName(String username) {
		User user = null;
		try {
			user = userRepository.findByUserName(username);
		} catch (Exception ex) {
			logger.error("Exception occurred while getting user by username from DB, Error : " + ex.getMessage());
		}
		return user;
	}

	@Override
	public boolean changeAdminPassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
		boolean passwordChanged = false;
		try {
			User user = userRepository.findById(userId).get();
			if (passwordEncoder.matches(oldPassword, user.getPassword()) && confirmPassword.equals(newPassword)) {
				user.setPassword(getEncodedPassword(newPassword));
				userRepository.save(user);
				passwordChanged = true;
			}			
		} catch (Exception e) {
			logger.error("Exception got while changing password, Error :" + e.getMessage());
		}
		return passwordChanged;
	}

	@Override
	public boolean validateAdmin1Passwords(int userId, String adminPassword) {
		boolean isValid = false;
		Admin admin = adminRepository.findByUserId(userId);
		Admin admin1 = admin;
		
		if (admin1 != null && passwordEncoder.matches(adminPassword, admin1.getAdminPassword())) {
			isValid = true;
		}
		return isValid;
	}
	
	@Override
	public boolean validateAdmin2Passwords(int userId, String adminPassword) {
		boolean isValid = false;
		Admin admin = adminRepository.findByUserId(userId);
		String systemId = admin.getSystemId();

		Admin admin2 = null;
		List<Admin> adminUsers = adminRepository.findAdminUsersBySystemIdExceptUser(systemId, userId);

		if (adminUsers.size() == 1) {
			admin2 = adminUsers.get(0);

			if (admin2 != null && passwordEncoder.matches(adminPassword, admin2.getAdminPassword())) {
				isValid = true;
			}
		}
		return isValid;
	}

	@Override
	public void startStopServer(int userId, String status) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		ServerStatus serverStatus = serverStatusRepository.findByUserId(userId);

		if (serverStatus != null) {
			serverStatus.setLastUpdated(dateFormat.format(new Date()));
			serverStatus.setStatus(status);
			serverStatusRepository.save(serverStatus);
		} else {
			serverStatus = new ServerStatus();
			serverStatus.setCreatedDate(dateFormat.format(new Date()));
			serverStatus.setUserId(userId);
			serverStatus.setStatus(status);
			serverStatusRepository.save(serverStatus);
		}

	}

	@Override
	public Admin InitiallizeAdmin(String admin1Password, String admin2Password, String systemId) {
		User admin1 = null;
		User admin2 = null;
		Admin admin = null;
		List<User> adminUsers = userRepository.findByRole("Admin");

		if (adminUsers.size() == 2) {
			admin1 = adminUsers.get(0);
			admin2 = adminUsers.get(1);
		}
		if (passwordEncoder.matches(admin1Password, admin1.getPassword()) && passwordEncoder.matches(admin2Password, admin2.getPassword())) {
			admin = new Admin();
			admin.setAdminName(admin1.getUserName());
			admin.setAdminPassword(getEncodedPassword(admin1Password));
			admin.setSystemId(systemId);
			admin.setUser(admin1);
			adminRepository.save(admin);
			
			admin = new Admin();
			admin.setAdminName(admin2.getUserName());
			admin.setAdminPassword(getEncodedPassword(admin2Password));
			admin.setSystemId(systemId);
			admin.setUser(admin2);
			adminRepository.save(admin);

		}
		return admin;
	}

}
