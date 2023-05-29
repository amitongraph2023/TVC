package com.tokens.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tokens.models.ServerStatus;
import com.tokens.models.User;
import com.tokens.repository.ServerStatusRepository;
import com.tokens.repository.UserRepository;

@Component
public class ServerStatusUtil {

	@Autowired
	private ServerStatusRepository serverStatusRepository;
	
	@Autowired
	private UserRepository userRepository;

	public boolean checkStatus() {
		boolean serverStart = false;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userRepository.findByUserName(username);

		ServerStatus serverStatus = serverStatusRepository.findBySystemId(user.getSystemId());
		if (serverStatus != null && serverStatus.getStatus().equals("start")) {
			return true;
		}
		return serverStart;
	}

}
