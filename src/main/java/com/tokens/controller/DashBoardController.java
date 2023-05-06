package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tokens.models.MasterKey;
import com.tokens.models.User;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.request.AuthRequest;
import com.tokens.request.MasterKeyRequest;
import com.tokens.service.TransactionService;
import com.tokens.service.UserService;

@Controller
public class DashBoardController {
	
//	1- number of transactions
//	2- total amount per location
//	3- top 5 customer
//	4- top 5 location
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MasterKeyRepository masterKeyRepository;
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		modelView.setViewName("home.html");
		modelView.addObject("transactionCount", transactionService.countAllTransactionofSystem(username));
		modelView.addObject("amountPerLocation","50" );
		modelView.addObject("topCustomer",transactionService.getTopCustomer(username));
		modelView.addObject("topLocation",transactionService.getTopLocations(username));
		return modelView;
	}
	
	@GetMapping("/signin")
	public ModelAndView login() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("authRequest", new AuthRequest());
		return modelView;
	}
	
	@GetMapping("/addMasterKey")
	public ModelAndView addMasterKey() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("masterKey.html");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findUserByUserName(username);
		MasterKey key = masterKeyRepository.findMasterKeyBySystemId(user.getSystemId());
		modelView.addObject("userId",user.getUserId());
		modelView.addObject("masterKey", key.getMasterKey());
		return modelView;
	}

	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("register.html");
		modelView.addObject("user", new User());
		return modelView;
	}
	
}
