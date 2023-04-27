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
	public boolean addOrUpdateMasterKey(int userId, String masterKey) {
		boolean isUpdated = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		try {
			User user = userRepository.findById(userId).get();
			MasterKey key = masterKeyRepository.findMasterKeyBySystemId(user.getSystemId());
            if(key != null && user.getRole().equals("Admin")) {
                key.setMasterKey(masterKey);
                key.setSystemId(user.getSystemId());
                key.setLastUpdated(dateFormat.format(new Date()));
                masterKeyRepository.save(key);
            	saveMasterKeyLogs(key);
				isUpdated = true;
            }else if(key == null){
				key = new MasterKey();
				key.setMasterKey(masterKey);
				key.setUserId(userId);
				key.setCreatedOn(dateFormat.format(new Date()));
				key.setSystemId(user.getSystemId());
				key = masterKeyRepository.save(key);
				saveMasterKeyLogs(key);
				isUpdated = true;
			}else if(user.getRole().equals("User")) {
				logger.error("User cannot update MasterKey");
				isUpdated = false;
			}
			
		} catch (Exception e) {
			logger.error("Exception got while adding User MasterKey, Error :"+e.getMessage());
		}
		return isUpdated;
	}

	@Override
	public void registerAdminOrUser(User user) {
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
	
	public void saveMasterKeyLogs(MasterKey masterKey) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		MasterKeyLogs logs = new MasterKeyLogs();
		logs.setMasterKeyId(masterKey.getMasterKeyId());
		logs.setMasterKey(masterKey.getMasterKey());
		logs.setUserId(masterKey.getUserId());
		logs.setCreatedOn(dateFormat.format(new Date()));
		masterKeyLogsRepository.save(logs);
		
	}
}
