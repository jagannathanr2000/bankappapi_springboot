package com.mycompany.authservice.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	

	//Old Key "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9"
	private static String SECRET_KEY = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
	private static int TOKEN_VALIDITY = 36000;
	
	public String extractUserName(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	//Extract Single Claim
	public <T> T extractClaims(String token,Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		System.out.println("--- "+claims+"---");
		return claimsResolver.apply(claims);
	}
	//Extracts All Claims
	public Claims extractAllClaims(String token) {
		return Jwts.parser()

				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private SecretKey getSignInKey() {
		// TODO Auto-generated method stub
		byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	//Method to Generate JWT Token
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
		
	}
	
	//Method to Generate JWT Token From UserDetails username
	public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + (TOKEN_VALIDITY)))
        .signWith(getSignInKey())
        .compact();
	}
	
	
	//To Check the token is valid
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String userName = extractUserName(token);
		System.out.println("--- "+token+" ---");
		if(userName.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
			return true;
		}
		
		return false;
	}
	
	//To Check the token is expired/not-expired
	private boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}
	
	private Date extractExpirationDate(String token) {
		return extractClaims(token,Claims::getExpiration);
	}
}
