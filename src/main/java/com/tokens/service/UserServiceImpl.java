package com.tokens.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokens.models.MasterKey;
import com.tokens.models.User;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	MasterKeyRepository masterKeyRepository;

	@Override
	@Transactional
	public boolean addOrUpdateMasterKey(int userId, String masterKey) {
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
				masterKeyRepository.save(key);
				isUpdated = true;
			} else {

				MasterKey key = masterKeyRepository.findAll().get(0);
				if (key != null && key.getUserId() == user.get().getUserId()) {
					logger.error("User cannont update the masterKey");
				} else {

					key.setMasterKey(masterKey);
					key.setUserId(userId);
					key.setCreatedOn(dateFormat.format(new Date()));
					masterKeyRepository.save(key);
					isUpdated = true;
				}
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
		user.setPassword(password);
		user.setRole(role);

		userRepository.save(user);
	}

}
