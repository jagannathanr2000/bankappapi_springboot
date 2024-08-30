package com.mycompany.authservice.dto;

public class AuthenticationResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}
	
	
}
