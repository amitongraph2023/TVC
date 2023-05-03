package com.tokens.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tokens.models.Transaction;
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
	
	@GetMapping("/dashboard")
	public ModelAndView countAllTransactions(){
		
		ModelAndView mvc = new ModelAndView("dashboard");
	
		mvc.addObject("transactionCount", transactionService.countAllTransaction());
		mvc.addObject("amountPerLocation","50");
		mvc.addObject("topCustomer","50");
		mvc.addObject("topLocation","5");

		return mvc;
	}
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home.html");;
		return modelView;
	}
	
	@GetMapping("/signin")
	public ModelAndView login() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("authRequest", new AuthRequest());
		return modelView;
	}
	
	@PreAuthorize("hasRole('user')")
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
