package com.mycompany.authservice.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;
	
//	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//		super();
//		this.jwtService = jwtService;
//		this.userDetailsService = userDetailsService;
//	}

	

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String email;
		
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request,response);
			return;
		}
		jwtToken = authHeader.substring(7);
		
		//Extract User Email From JWT Token
		email = jwtService.extractUserName(jwtToken);
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
			if(jwtService.isTokenValid(jwtToken,userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(
						new WebAuthenticationDetailsSource()
						.buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
			
		}
		filterChain.doFilter(request,response);
	}

}
