package com.tokens.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tokens.exceptions.CredentialValidationException;
import com.tokens.models.MasterKeyLogs;
import com.tokens.models.ServerStatus;
import com.tokens.models.TransactionStatusLogs;
import com.tokens.models.User;
import com.tokens.request.AdminRequest;
import com.tokens.request.AuthRequest;
import com.tokens.request.ChangePasswordRequest;
import com.tokens.request.MasterKeyRequest;
import com.tokens.request.ValidatePasswordRequest;
import com.tokens.response.AuthResponse;
import com.tokens.service.UserService;
import com.tokens.utils.JwtCookieUtil;
import com.tokens.utils.JwtUtil;
import com.tokens.utils.ServerStatusUtil;

@RestController
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class.getName());

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired 
	UserService userService;

	@Autowired
	ServerStatusUtil serverStatusUtil;

	@PostMapping("/user/registerUser")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			userService.registerAdminOrUser(user);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

		return ResponseEntity.ok().body("SuccessFully Registered User");
	}

	@PostMapping("/user/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		if (authRequest.getUserName() == null || authRequest.getUserName().trim().equals("")) {
			throw new com.tokens.exceptions.InvalidAuthStringException();
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			log.error("Error occurred due to Invalid credentials");
            return ResponseEntity.badRequest().body("Invalid username/password");
		} catch (DisabledException e) {
			log.error("Error occurred due to the User is disabled");
            return ResponseEntity.badRequest().body("User is disabled");
		} catch (Exception e) {
			log.error("Exception occurred");
            return ResponseEntity.badRequest().body("Failed to authenticate");
		}
		return ResponseEntity.ok(createJwtToken(authRequest.getUserName()));
	}

	public AuthResponse createJwtToken(String userName) throws CredentialValidationException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		try {
			String newGeneratedToken = jwtUtil.generateToken(userName);
			JwtCookieUtil.addTokenCookies(attr, newGeneratedToken, "true");
			User user = userService.findUserByUserName(userName);
			return new AuthResponse(user.getRole(),newGeneratedToken);
			
		} catch (Exception ex) {
			log.error("User = {} failed auth", userName, ex);
			JwtCookieUtil.clearTokenCookies(attr);
			throw new CredentialValidationException("incorrect credentials");
		}
		
	}

	@PostMapping("/user/updateMasterKey")
	public ResponseEntity<String> addOrUpdateMasterKey(@RequestBody MasterKeyRequest request) {
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		Boolean isAdded = userService.addOrUpdateMasterKey(Integer.parseInt(request.getUserId()),
				request.getMasterKey());
		if (isAdded) {
			return ResponseEntity.ok().body("Successfully added User MasterKey");
		}

		return ResponseEntity.badRequest().body("User cannot update MasterKey");
	}

	@GetMapping("/user/getMasterKeyLogs/{id}")
	public ResponseEntity<?> getMasterKeyLogs(@PathVariable("id") int userId) {
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		List<MasterKeyLogs> masterKeyLogs = userService.getAllMasterKeyLogs(userId);
		if (masterKeyLogs != null) {
			return ResponseEntity.ok().body(masterKeyLogs);
		}
		return ResponseEntity.badRequest().body("Exception occurred while getting master key logs");
	}
	

	@PutMapping("/users/changeAdminPassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		boolean passwordChanged = userService.changeAdminPassword(changePasswordRequest.getUserId(), changePasswordRequest.getOldPassword() 
								, changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmPassword());

		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
		if (passwordChanged) {
			return ResponseEntity.ok("Password changed successfully.");
		} else {
			return ResponseEntity.badRequest().body("Password doesn't match");
		}
	}
	
	@PostMapping("/validateAdmin1Passwords")
    public ResponseEntity<?> validateAdmin1Passwords(@RequestBody ValidatePasswordRequest validatePasswordRequest) {
		
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
        boolean validPasswords = userService.validateAdmin1Passwords(validatePasswordRequest.getUserId(), 
        		validatePasswordRequest.getAdminPassword());
        
        if (validPasswords) {
            return ResponseEntity.ok().body("Password Successfully validated");
        } else {
            return ResponseEntity.badRequest().body("Incorrect passwords.");
        }
    }
	
	@PostMapping("/validateAdmin2Passwords")
    public ResponseEntity<?> validateAdmin2Passwords(@RequestBody ValidatePasswordRequest validatePasswordRequest) {
		
		boolean serverStart = serverStatusUtil.checkStatus();
		if (!serverStart) {
			return ResponseEntity.badRequest().body("Curently System is stopped");
		}
        boolean validPasswords = userService.validateAdmin2Passwords(validatePasswordRequest.getUserId(), 
        		validatePasswordRequest.getAdminPassword());
        
        if (validPasswords) {
            return ResponseEntity.ok().body("Password Successfully validated");
        } else {
            return ResponseEntity.badRequest().body("A System must have exactly two admin users. Please make sure You have provided the correct passwords.");
        }
    }
	
	@GetMapping("/startStopServer/{id}/{status}")
	public ResponseEntity<?> startServer(@PathVariable("id") int userId, @PathVariable("status") String status) {
		try {
			userService.startStopServer(userId, status);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

		return ResponseEntity.ok().body("Success");	
	}

	@PostMapping("/IntiallizeAdmin")
	public ResponseEntity<?> initiallizeAdmin(@RequestBody AdminRequest adminRequest) {
		try {
			userService.InitiallizeAdmin(adminRequest.getAdmin1Password(), adminRequest.getAdmin2Password(),
					adminRequest.getsystemId());
		}
		catch (Exception ex) {
			return ResponseEntity.badRequest().body("UserId already exists");
		}
		return ResponseEntity.ok().body("Success");
	}
	
	
}
