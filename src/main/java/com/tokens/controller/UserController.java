package com.tokens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokens.models.User;
import com.tokens.request.AuthRequest;
import com.tokens.request.MasterKeyRequest;
import com.tokens.service.UserService;
import com.tokens.utils.JwtUtil;

@RestController
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@PostMapping("/user/registerUser")
	public ResponseEntity<String> regsiterUser(@RequestBody User user) {

		userService.registerAdminOrUser(user);

		return ResponseEntity.ok().body("SuccessFully Registered User");
	}
	
	@PostMapping("/user/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new Exception("inavalid username/password, Error : "+ex.getMessage());
		}
		return jwtUtil.generateToken(authRequest.getUserName());
	}


	@PostMapping("/user/updateMasterKey")
	@ResponseBody
	public ResponseEntity<String> addOrUpdateMasterKey(@RequestBody MasterKeyRequest request) {

		Boolean isAdded = userService.addOrUpdateMasterKey(Integer.parseInt(request.getUserId()), request.getMasterKey());
		if (isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}

		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}
	
	

}
