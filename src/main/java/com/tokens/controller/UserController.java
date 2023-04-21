package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tokens.service.UserService;

@Controller("/api/user/")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("addMasterKey")
	@ResponseBody
	public ResponseEntity<String> addUserMasterKey(@RequestParam String userId,@RequestParam String masterKey ){
		
		Boolean isAdded = userService.addUserMasterKey(Integer.parseInt(userId), masterKey);
		if(isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}
		
		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}
}
