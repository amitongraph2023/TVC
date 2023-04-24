package com.tokens.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tokens.models.Transaction;
import com.tokens.request.CloudRequest;
import com.tokens.request.TransactionStatusRequest;
import com.tokens.response.CloudResponse;
import com.tokens.service.TransactionService;

@Controller("/api/tokens/")
public class TokenController {
	
	@Autowired
	@Qualifier("")
	TransactionService transactionService;
	
	
	@GetMapping("/generateToken")
	public ResponseEntity<CloudResponse> generateTransactionToken(@RequestBody CloudRequest cloudRequest){
		
		CloudResponse res = transactionService.generateTransactionToken(cloudRequest);
		if(res != null) {
			return ResponseEntity.ok().body(res);
		}
		return ResponseEntity.badRequest().body(new CloudResponse());
	}

	@PostMapping("/updateStatus")
    public ResponseEntity<String> updateTransactionStatus(@RequestBody TransactionStatusRequest request) {
        boolean updated = transactionService.updateTransactionStatus(Integer.parseInt(request.getTransactionId()), request.getStatus());
        if (updated) {
        	return ResponseEntity.ok().body("Successfully updated");
        }
        
        return ResponseEntity.ok().body("Exception during updating transaction status");
    }
	
	@GetMapping("/getLogsUpdatedStatus")
	public ResponseEntity<List<Transaction>> logsUpdatedTransactionStatus(){
		List<Transaction> transaction = transactionService.logsUpdatedTransactionStatus();
		if (transaction != null) {
			return ResponseEntity.ok().body(transaction);
		}
		return ResponseEntity.badRequest().body(transaction);
	}   
}
