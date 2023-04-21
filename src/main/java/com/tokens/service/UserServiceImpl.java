package com.tokens.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokens.models.User;
import com.tokens.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public boolean addUserMasterKey(Integer userId, String masterKey) {
		Boolean isUpdated = false;
		try {
			Optional<User> user = userRepository.addUserMasterKey(userId, masterKey);
			if (user != null && user.get() != null && user.get().getMasterKey().equals(masterKey)) {
				isUpdated = true;
			}
		} catch (Exception e) {
			logger.error("Exception got while adding User MasterKey, UserId : " + userId);
		}
		return isUpdated;
	}

}
