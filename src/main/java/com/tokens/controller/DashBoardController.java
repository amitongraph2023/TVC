package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tokens.models.Transaction;
import com.tokens.service.TransactionService;

@Controller("/view")
public class DashBoardController {

//	1- number of transactions
//	2- total amount per location
//	3- top 5 customer
//	4- top 5 location
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("/dashboard")
	public ModelAndView countAllTransactions(){
		
		ModelAndView mvc = new ModelAndView("dashboard");
	
		mvc.addObject("transactionCount", transactionService.countAllTransaction());
		mvc.addObject("amountPerLocation","50");
		mvc.addObject("topCustomer","50");
		mvc.addObject("topLocation","5");

		return mvc;
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
