package com.hexaware.cozyheaven.hotelbooking.restController;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cozyheaven.hotelbooking.dto.AuthRequest;
import com.hexaware.cozyheaven.hotelbooking.dto.UserSummaryDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.service.IGuestService;
import com.hexaware.cozyheaven.hotelbooking.service.JwtService;
import com.hexaware.cozyheaven.hotelbooking.service.UserService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/users")
public class AuthController {
	
	@Autowired
	private IGuestService guestService;
	

	@Autowired
	UserService service;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@PostMapping("/registration/new")
	public String addNewUser(@RequestBody User user) {
		
		return service.addUser(user);
	}

	

	@PostMapping("/login/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		logger.info("alert1");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		logger.info("alert2");
		

		String token = null;

		if (authentication.isAuthenticated()) {

			// call generate token method from jwtService class

			token = jwtService.generateToken(authRequest.getEmail());

			logger.info("Token : " + token);

		} else {

			logger.info("invalid");

			throw new UsernameNotFoundException("Email or Password in Invalid / Invalid Request");

		}
		
		return token;
		

	}
	
	@GetMapping("/userId")
	 public ResponseEntity<?> getUserSummary(@RequestParam String fullName) {
	     Optional<UserSummaryDTO> summaryOpt = service.getUserSummaryByEmail(fullName);

	     if (summaryOpt.isPresent()) {
	         return ResponseEntity.ok(summaryOpt.get());
	     } else {
	         return ResponseEntity.status(404).body("User not found");
	     }
	 }
	



}