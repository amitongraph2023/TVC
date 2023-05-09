package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.tokens.models.MasterKey;
import com.tokens.models.User;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.request.AuthRequest;
import com.tokens.service.TransactionService;
import com.tokens.service.UserService;
import com.tokens.service.CustomerService;

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
	CustomerService customerService;
	
	@Autowired
	MasterKeyRepository masterKeyRepository;
	

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUserName(username);
        return user;
    }
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();
		User user = getUser();
		modelView.setViewName("home.html");
		modelView.addObject("transactionCount", transactionService.countAllTransactionofSystem(user.getUserName()));
		modelView.addObject("amountPerLocation","50" );
		modelView.addObject("Customer",customerService.getTopCustomer(user.getUserName()));
		modelView.addObject("topLocation",transactionService.getTopLocations(user.getUserName()));
		modelView.addObject("userId", user.getUserId());
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
		User user = getUser();
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
	
	@GetMapping("/transactionStatusLogs/{id}")
	public ModelAndView transactionStatusLogs(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("TransactionStatusLogs.html");
		modelView.addObject("TransactionStatusLogs", transactionService.getTransactionStatusLogs(userId));
		return modelView;
	}
	
	@GetMapping("/masterKeyLogs/{id}")
	public ModelAndView masterKeyLogs(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("masterKeyLogs.html");
		modelView.addObject("MasterKeyLogs", userService.getAllMasterKeyLogs(userId));
		return modelView;
	}
	
	@GetMapping("/transactionLogs/{id}")
	public ModelAndView tranLogs(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("tranLogs.html");
		modelView.addObject("TransactionLogs", transactionService.logsTransactionToken(userId));
		return modelView;
	}
}
