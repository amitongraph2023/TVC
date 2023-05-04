package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tokens.models.User;
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
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home.html");;
		modelView.addObject("transactionCount", transactionService.countAllTransaction());
		modelView.addObject("amountPerLocation","50");
		modelView.addObject("topCustomer","5");
		modelView.addObject("topLocation","5");
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
		modelView.addObject("user",user);
		modelView.addObject("masterKeyRequest", new MasterKeyRequest());
		return modelView;
	}
	
//	
//	@GetMapping("/getSuccessTransaction")
//	public Transaction getSuccessTransaction() {
//		Transaction transaction = transactionService.getSuccessTransactions();
//		return transaction;
//	}
//	
//	@GetMapping("/getFailedTransaction")
//	public Transaction getFailedTransaction() {
//		Transaction transaction = transactionService.getFailedTransactions();
//		return transaction;
//	}
	
}
