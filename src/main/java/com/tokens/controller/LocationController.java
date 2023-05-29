package com.tokens.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tokens.models.Location;
import com.tokens.service.LocationService;
import com.tokens.utils.ServerStatusUtil;

@RestController
public class LocationController {
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	ServerStatusUtil serverStatusUtil;
	
	@PostMapping("/addMerchant")
	public ResponseEntity<String> addMerchant(@RequestBody Location location) {
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		
		if (location.getMerchantId() == null) {
	        return ResponseEntity.badRequest().body("Merchant ID is null");
	    }
	    
	    if (location.getMerchantName() == null || location.getMerchantName().equals("")) {
	        return ResponseEntity.badRequest().body("Name is null");
	    }
	    
		try {
			locationService.addMerchant(location);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().body("Successfully added");
		
	}
	
	
	@GetMapping("/deleteMerchant/{merchantId}")
	public ResponseEntity<String> removeMerchant(@PathVariable("merchantId") int merchantId) {
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		
		try {
			locationService.removeMerchantById(merchantId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Id not exist");
		}
		return ResponseEntity.ok().body("Successfully Removed");

	}


	
}
