package com.tokens.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.tokens.models.Location;
import com.tokens.models.MasterKey;
import com.tokens.models.User;
import com.tokens.repository.MasterKeyRepository;
import com.tokens.request.AuthRequest;
import com.tokens.request.ChangePasswordRequest;
import com.tokens.service.TransactionService;
import com.tokens.service.UserService;
import com.tokens.utils.ServerStatusUtil;
import com.tokens.service.CustomerService;
import com.tokens.service.LocationDtoService;
import com.tokens.service.LocationService;

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
	LocationDtoService locationDtoService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	MasterKeyRepository masterKeyRepository;
	
	@Autowired
	ServerStatusUtil serverStatusUtil;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUserName(username);
        return user;
    }
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();		    		
		modelView.setViewName("home.html");
		User user = getUser();
		modelView.addObject("userId", user.getUserId());
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }		    
		modelView.addObject("transactionCount", transactionService.countAllTransactionofSystem(user.getUserId()));
		modelView.addObject("Customer",customerService.getTopCustomer(user.getUserId()));
		modelView.addObject("topLocation",transactionService.getTopLocations(user.getUserId()));
		
		return modelView;
	}
	
	
	@GetMapping("/signin")
	public ModelAndView login() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("authRequest", new AuthRequest());
		return modelView;
	}
	
	@GetMapping("/addMasterKey/{id}")
	public ModelAndView addMasterKey(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("masterKey.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }				
		Optional<MasterKey> masterKeyOptional = masterKeyRepository.findById(userId);
		if(masterKeyOptional.isPresent()) {
			modelView.addObject("masterKey", masterKeyOptional.get().getMasterKey());
			
		} else {
			modelView.addObject("masterKey", "");
		}
		modelView.addObject("userId",userId);		
		return modelView;
	}
	
	@GetMapping("/transactionStatusLogs/{id}")
	public ModelAndView transactionStatusLogs(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("TransactionStatusLogs.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }
		modelView.addObject("TransactionStatusLogs", transactionService.getTransactionStatusLogs(userId));
		return modelView;
	}
	
	@GetMapping("/transactionLogs/{id}")
	public ModelAndView transactionLogs(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("transactionLogs.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }
		modelView.addObject("TransactionLogs", transactionService.logsTransactionToken(userId));
		return modelView;
	}
	
	@GetMapping("/amountPerlocation/{id}")
	public ModelAndView amountPerLocation(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("amountPerLocation.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }
		modelView.addObject("AmountPerLocation", locationDtoService.getAmountPerLocation(userId));
		return modelView;
	}
	
	@GetMapping("/addLocation")
	public ModelAndView addMerchant() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("AddLocation.html");
		modelView.addObject("location", new Location());
		return modelView;
	}
	
	@GetMapping("/getLocation")
	public ModelAndView getMerchant() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("showLocation.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }
		modelView.addObject("Location", locationService.getLocations());
		return modelView;
	}

	@GetMapping("/changePassword/{id}")
	public ModelAndView changePassword(@PathVariable("id") int userId) {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("changePassword.html");
		boolean serverStart = serverStatusUtil.checkStatus();
	    if (!serverStart) {
	        return modelView;
	    }
		modelView.addObject("userId",userId);
		modelView.addObject("changePasswordRequest", new ChangePasswordRequest());
		return modelView;
	}

}
