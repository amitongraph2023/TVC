package com.tokens.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tokens.exceptions.CredentialValidationException;
import com.tokens.models.MasterKeyLogs;
import com.tokens.models.User;
import com.tokens.request.AuthRequest;
import com.tokens.request.MasterKeyRequest;
import com.tokens.response.AuthResponse;
import com.tokens.service.UserService;
import com.tokens.utils.JwtCookieUtil;
import com.tokens.utils.JwtUtil;

@RestController
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class.getName());

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
	public AuthResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		if (authRequest.getUserName() == null || authRequest.getUserName().trim().equals("")) {
			throw new com.tokens.exceptions.InvalidAuthStringException();
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			log.error("Error because of Invalid credentials");
			return new AuthResponse();
		} catch (DisabledException e) {
			log.error("Error because the User is disabled");
			return new AuthResponse();
		} catch (Exception e) {
			log.error("Exception occurred");
			return new AuthResponse();
		}
		return createJwtToken(authRequest.getUserName());
	}

	public AuthResponse createJwtToken(String userName) throws CredentialValidationException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		try {
			String newGeneratedToken = jwtUtil.generateToken(userName);
			JwtCookieUtil.addTokenCookies(attr, newGeneratedToken, "true");
			return new AuthResponse(newGeneratedToken);
		} catch (Exception ex) {
			log.error("User = {} failed auth", userName, ex);
			JwtCookieUtil.clearTokenCookies(attr);
			throw new CredentialValidationException("incorrect credentials");
		}
	}

	@PostMapping("/user/updateMasterKey")
	public ResponseEntity<String> addOrUpdateMasterKey(@RequestBody MasterKeyRequest request) {

		Boolean isAdded = userService.addOrUpdateMasterKey(Integer.parseInt(request.getUserId()),
				request.getMasterKey());
		if (isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}

		return ResponseEntity.badRequest().body("Exception got while added MasterKey");
	}

	@GetMapping("/user/getMasterKeyLogs/{id}")
	public ResponseEntity<List<MasterKeyLogs>> getMasterKeyLogs(@PathVariable("id") int userId) {
		return ResponseEntity.ok().body(userService.getAllMasterKeyLogs(userId));
	}

}
