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

import com.tokens.request.AuthRequest;
import com.tokens.service.UserService;
import com.tokens.utils.JwtUtil;

@Controller("/api/user/")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@PostMapping("/registerAdminUser")
	public ResponseEntity<String> regsiterAdmin(@RequestBody AuthRequest request) {

		userService.registerAdminOrUser(request.getUserName(), request.getPassword(), "Admin");

		return ResponseEntity.ok().body("SuccessFully Registered Admin User");
	}

	@PostMapping("/registerUser")
	public ResponseEntity<String> regsiterUser(@RequestBody AuthRequest request) {

		userService.registerAdminOrUser(request.getUserName(), request.getPassword(), "User");

		return ResponseEntity.ok().body("SuccessFully Registered User");
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtUtil.generateToken(authRequest.getUserName());
	}


	@PostMapping("/updateMasterKey")
	@ResponseBody
	public ResponseEntity<String> addOrUpdateMasterKey(@RequestParam String userId, @RequestParam String masterKey) {

		Boolean isAdded = userService.addOrUpdateAdminMasterKey(Integer.parseInt(userId), masterKey);
		if (isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}

		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}
	
	@PostMapping("/addMasterKey")
	@ResponseBody
	public ResponseEntity<String> addMasterKey(@RequestParam String userId, @RequestParam String masterKey) {

		Boolean isAdded = userService.addUserMasterKey(Integer.parseInt(userId), masterKey);
		if (isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}

		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}

}
