package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tokens.request.AuthRequest;
import com.tokens.service.UserService;

@Controller("/api/user/")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/registerAdminUser")
	public ResponseEntity<String> regsiterAdmin(@RequestBody AuthRequest request){
		
		userService.registerAdminOrUser(request.getUserName(), request.getPassword(),"Admin");
		
		return ResponseEntity.ok().body("SuccessFully Registered Admin User");
	}
	
	@PostMapping("/registerUser")
	public ResponseEntity<String> regsiterUser(@RequestBody AuthRequest request){
		
		userService.registerAdminOrUser(request.getUserName(), request.getPassword(),"User");
		
		return ResponseEntity.ok().body("SuccessFully Registered User");
	}
	
	@PostMapping("/updateMasterKey")
	@ResponseBody
	public ResponseEntity<String> addOrUpdateMasterKey(@RequestParam String userId,@RequestParam String masterKey){
		
		Boolean isAdded = userService.addOrUpdateMasterKey(Integer.parseInt(userId), masterKey);
		if(isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}
		
		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}
}
