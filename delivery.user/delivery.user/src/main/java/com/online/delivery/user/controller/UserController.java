package com.online.delivery.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.online.delivery.user.dto.AuthRequest;
import com.online.delivery.user.entities.User;
import com.online.delivery.user.security.JwtUtil;
import com.online.delivery.user.services.CustomeUserDetailsService;
import com.online.delivery.user.services.UserService;

@RestController 
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	UserService userService;

	@Autowired
	CustomeUserDetailsService myUserDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	AuthRequest authRequest;

	@PostMapping("/user/register")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

//	@PostMapping("/customer/register")
//	public User addCustomer(@RequestBody Customer user) {
//		return userService.addCustomer(user);
//	}
//
//	@PostMapping("/vendor/register")
//	public User addVendor(@RequestBody Customer user) {
//		return userService.addVendor(user);
//	}
//
//	@PostMapping("/admin/register")
//	public User addAdmin(@RequestBody Customer user) {
//		return userService.addAdmin(user);
//	}
//
//	@PostMapping("/deliverypersonnel/register")
//	public User addDeliveryPersonnel(@RequestBody Customer user) {
//		return userService.addDeliveryPersonnel(user);
//	}

	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		logger.info("Registering user: {}", user);
		userService.addUser(user);
		UserDetails userDetails = user;
		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
		String token = jwtUtil.generateToken(userDetails);
		return token;
	}

//	@PostMapping("/login")
//	public ResponseEntity<Object> login(@RequestBody AuthRequest user) throws Exception {
//		logger.info("user: " + user);
//		String token = null;
//		try {
//			authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//		} catch (BadCredentialsException e) {
//			logger.info("BadCredentialsException: " + e.getMessage());
//		} catch (Exception e) {
//            logger.info("Exception: " + e.getMessage());
//		}
//		UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
//		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
//		if (!userDetails.getAuthorities().isEmpty()) {
//			token = jwtUtil.generateToken(userDetails);
//			
//			return ResponseEntity.ok(token);
//		}
//		return ResponseEntity.status(403).build();
//	}

	@PostMapping("/login/customer")
	public ResponseEntity<Object> loginCustomer(@RequestBody AuthRequest user) throws Exception {
		logger.info("user: " + user);
		String token = null;
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			logger.info("BadCredentialsException: " + e.getMessage());
		} catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
		}
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
		if (userDetails.getAuthorities().toString().contains("CUSTOMER")) {
			token = jwtUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}

	@PostMapping("/login/vendor")
	public ResponseEntity<Object> loginVendor(@RequestBody AuthRequest user) throws Exception {
		logger.info("user: " + user);
		String token = null;
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			logger.info("BadCredentialsException: " + e.getMessage());
		} catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
		}
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
		if (userDetails.getAuthorities().toString().contains("VENDOR")) {
			token = jwtUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}

	@PostMapping("/login/admin")
	public ResponseEntity<Object> loginAdmin(@RequestBody AuthRequest user) throws Exception {
		logger.info("user: " + user);
		String token = null;
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			logger.info("BadCredentialsException: " + e.getMessage());
		} catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
		}
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
		if (userDetails.getAuthorities().toString().contains("ADMIN")) {
			token = jwtUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}

	@PostMapping("/login/deliverypersonnel")
	public ResponseEntity<Object> loginDeliveryPeronnel(@RequestBody AuthRequest user) throws Exception {
		logger.info("user: " + user);
		String token = null;
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			logger.info("BadCredentialsException: " + e.getMessage());
		} catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
		}
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
		userDetails.getAuthorities().forEach(auth -> logger.info("User role: " + auth.getAuthority()));
		if (userDetails.getAuthorities().toString().contains("DELIVERY_PERSONNEL")) {
			token = jwtUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}
}
