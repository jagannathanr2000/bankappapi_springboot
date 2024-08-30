package com.mycompany.authservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.authservice.dto.AuthenticationRequest;
import com.mycompany.authservice.dto.AuthenticationResponse;
import com.mycompany.authservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
		return ResponseEntity.ok(authService.registerUser(authRequest));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
		return ResponseEntity.ok(authService.authenticateUser(authRequest));
	}
	
	@PostMapping("/authorize")
	public ResponseEntity<Map<String,Boolean>> authorizeUser(@RequestHeader("Authorization") String token) {
		Map<String,Boolean> response = new HashMap<>();
		
		boolean status = authService.authorizeUser(token);
		response.put("status",status);
		return ResponseEntity.ok(response);
	
	}
	
}
