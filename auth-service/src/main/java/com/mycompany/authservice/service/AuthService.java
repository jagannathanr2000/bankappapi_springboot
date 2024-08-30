package com.mycompany.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.authservice.dto.AuthenticationRequest;
import com.mycompany.authservice.dto.AuthenticationResponse;
import com.mycompany.authservice.entity.Role;
import com.mycompany.authservice.entity.User;
import com.mycompany.authservice.jwt.JwtService;
import com.mycompany.authservice.repository.AuthRepository;

@Service
public class AuthService {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthRepository authRepo;
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthenticationResponse registerUser(AuthenticationRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.ROLE_USER);
		authRepo.save(user);
		AuthenticationResponse response = new AuthenticationResponse();
		String token = jwtService.generateToken(user);
		response.setToken(token);
		return response;
		
	}
	
	public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword())
				);
		
		User user = authRepo.findById(request.getEmail()).orElseThrow();
		
		AuthenticationResponse response = new AuthenticationResponse();
		String token = jwtService.generateToken(user);
		response.setToken(token);
		return response;
		
	}
	
	public boolean authorizeUser(String token) {
		
		String email = jwtService.extractUserName(token);
		UserDetails user = userDetailsService.loadUserByUsername(email);
		System.out.println("--- "+user.toString()+" ---");
		return jwtService.isTokenValid(token, user);
	}
}
