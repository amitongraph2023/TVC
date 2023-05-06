package com.tokens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokens.models.Transaction;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.request.CloudRequest;
import com.tokens.request.TransactionStatusRequest;
import com.tokens.response.CloudResponse;
import com.tokens.service.TransactionService;

@RestController
public class TokenController {
	
	@Autowired
	TransactionService transactionService;
	
	
	@PostMapping("/transaction/generateToken")
	public ResponseEntity<CloudResponse> generateTransactionToken(@Validated @RequestBody CloudRequest cloudRequest, BindingResult result){
		
		if (result.hasErrors()) {
            // Build error message and return bad request response
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new CloudResponse("", (long)0, errorMessage.toString()));
        }
		
		CloudResponse res = transactionService.generateTransactionToken(cloudRequest);
		if(res != null) {
			return ResponseEntity.ok().body(res);
		}
		return ResponseEntity.badRequest().body(new CloudResponse());
	}

	@GetMapping("/transaction/getLogsTransactionToken")
	public ResponseEntity<List<Transaction>> logsTransactionToken(){
		List<Transaction> transactionTokenLog = transactionService.logsTransactionToken();
		if (transactionTokenLog != null) {
			return ResponseEntity.ok().body(transactionTokenLog);
		}
		return ResponseEntity.badRequest().body(transactionTokenLog);
	}
	
	
	@PostMapping("/transaction/updateTransactionStatus")
    public ResponseEntity<String> updateTransactionStatus(@RequestBody TransactionStatusRequest request) {
        boolean updated = transactionService.updateTransactionStatus(Integer.parseInt(request.getTransactionId()), request.getStatus());
        if (updated) {
        	return ResponseEntity.ok().body("Successfully updated");
        }
        return ResponseEntity.ok().body("Exception during updating transaction status");
    }
	
	@GetMapping("/transaction/getTransactionStatusLogs")
	public ResponseEntity<List<TransactionStatusLogs>> getTransactionStatusLogs(){
		List<TransactionStatusLogs> transactionLog = transactionService.getTransactionStatusLogs();
		if (transactionLog != null) {
			return ResponseEntity.ok().body(transactionLog);
		}
		return ResponseEntity.badRequest().body(transactionLog);
	}   
	

}
