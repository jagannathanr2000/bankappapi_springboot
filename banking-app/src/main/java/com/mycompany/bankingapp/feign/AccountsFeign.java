package com.mycompany.bankingapp.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(name="auth-service",url="http://localhost:8090/api/v1/auth")
public interface AccountsFeign {
	
	@PostMapping("/authorize")
	public ResponseEntity<Map<String,Boolean>> authorizeUser(@RequestHeader("Authorization") String token) ;
	
	
	
}
