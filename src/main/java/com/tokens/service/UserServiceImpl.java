package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tokens.models.MasterKey;
import com.tokens.models.MasterKeyLogs;
import com.tokens.models.User;
import com.tokens.repository.MasterKeyLogsRepository;
import com.tokens.repository.MasterKeyRepository;
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

	@Autowired(required=true)
	BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public boolean addOrUpdateAdminMasterKey(int userId, String masterKey) {
		Boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		try {
			Optional<User> user = userRepository.findById(userId);

			if (user.get().getRole().equalsIgnoreCase("Admin")) {

				MasterKey key = masterKeyRepository.findAll().get(0);
				key.setMasterKey(masterKey);
				key.setUserId(userId);
				key.setCreatedOn(dateFormat.format(new Date()));
				key.setLastUpdated(dateFormat.format(new Date()));
				key = masterKeyRepository.save(key);
				saveMasterKeyLogs(key);
				isUpdated = true;
			}
		} catch (Exception e) {
			logger.error("Exception got while adding User MasterKey, UserId : " + userId);
		}
		return isUpdated;
	}

	@Override
	@Transactional
	public boolean addUserMasterKey(int userId, String masterKey) {
		Boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		try {
			Optional<User> user = userRepository.findById(userId);

			MasterKey key = masterKeyRepository.findAll().get(0);
			if (key != null && key.getUserId() == user.get().getUserId()) {
				logger.error("User cannont update the masterKey");
				throw new Exception("User cannont update the masterKey");
			} else {

				key.setMasterKey(masterKey);
				key.setUserId(userId);
				key.setCreatedOn(dateFormat.format(new Date()));
				key = masterKeyRepository.save(key);
				saveMasterKeyLogs(key);

				isUpdated = true;
			}
		} catch (Exception e) {
			logger.error("Exception got while adding User MasterKey, UserId : " + userId);
		}
		return isUpdated;
	}

	@Override
	public void registerAdminOrUser(String userName, String password, String role) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(getEncodedPassword(password));
		user.setRole(role);
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
	
	public void saveMasterKeyLogs(MasterKey masterKey) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		MasterKeyLogs logs = new MasterKeyLogs();
		logs.setMasterKeyId(masterKey.getMasterKeyId());
		logs.setMasterKey(masterKey.getMasterKey());
		logs.setUserId(masterKey.getUserId());
		logs.setCreatedOn(dateFormat.format(new Date()));
	}
}
