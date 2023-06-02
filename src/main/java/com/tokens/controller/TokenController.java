package com.tokens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.request.CloudRequest;
import com.tokens.request.TransactionStatusRequest;
import com.tokens.response.CloudResponse;
import com.tokens.service.TransactionService;
import com.tokens.utils.ServerStatusUtil;

@RestController
public class TokenController {
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	ServerStatusUtil serverStatusUtil;
	
	@PostMapping("/transaction/generateToken")
	public ResponseEntity<CloudResponse> generateTransactionToken(@Validated @RequestBody CloudRequest cloudRequest, BindingResult result){
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body(new CloudResponse("", "", "Curently System is stopped"));
		}
		
		if (result.hasErrors()) {
            // Build error message and return bad request response
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new CloudResponse("", "", errorMessage.toString()));
        }
		
		CloudResponse res = transactionService.generateTransactionToken(cloudRequest);
		if(res != null) {
			return ResponseEntity.ok().body(res);
		}
		return ResponseEntity.badRequest().body(new CloudResponse());
	}

	@GetMapping("/transaction/getLogsTransactionToken/{id}")
	public ResponseEntity<?> logsTransactionToken(@PathVariable("id") int userId){
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		
		List<Transaction> transactionTokenLog = transactionService.logsTransactionToken(userId);
		if (transactionTokenLog != null && transactionTokenLog.size() > 0) {
			return ResponseEntity.ok().body(transactionTokenLog);
		}
		return ResponseEntity.badRequest().body("No logs exists for this user");
	}
	
	
	@PostMapping("/transaction/updateTransactionStatus")
    public ResponseEntity<String> updateTransactionStatus(@RequestBody TransactionStatusRequest request) {
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		
        String response = transactionService.updateTransactionStatus(request.getTransactionId(), request.getStatus());
        if (response != "" && response.equals("success")) {
        	return ResponseEntity.ok().body("Successfully updated");
        }
        return ResponseEntity.badRequest().body(response);
    }
	
	@GetMapping("/transaction/getTransactionStatusLogs/{id}")
	public ResponseEntity<?> getTransactionStatusLogs(@PathVariable("id") int userId){
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		
		List<TransactionStatusLogs> transactionLog = transactionService.getTransactionStatusLogs(userId);
		if (transactionLog != null && transactionLog.size() > 0) {
			return ResponseEntity.ok().body(transactionLog);
		}
		return ResponseEntity.badRequest().body("No logs exists for this user");
	}   
	

}
