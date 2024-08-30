package com.mycompany.bankingapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {
	
	@ExceptionHandler(value=AccountNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleAccountNotFound(AccountNotFoundException e) {
		Map<String,String> body = new HashMap<>();
		body.put("message",e.getMessage());
		return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(value=Exception.class)
//	public ResponseEntity<Map<String,String>> handleOtherException(Exception e) {
//		Map<String,String> body = new HashMap<>();
//		body.put("message","Server Error!!!");
//		return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
