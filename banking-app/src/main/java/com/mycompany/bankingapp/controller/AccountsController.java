package com.mycompany.bankingapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.bankingapp.dto.AccountDTO;
import com.mycompany.bankingapp.feign.AccountsFeign;
import com.mycompany.bankingapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/api/accounts")

public class AccountsController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
	@Autowired
	public AccountService accountService;
	@Autowired
	public AccountsFeign accountsFeign;
	//Add new account API
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accRequest) {
		logger.warn("NEW_ACCOUNT_ADDED");
		return new ResponseEntity<>(accountService.createNewAccount(accRequest),HttpStatus.CREATED);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable long id) {
		
		return ResponseEntity.ok(accountService.getAccountByID(id));
	}
	
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDTO> updateAccountBalance(@PathVariable long id, @RequestBody Map<String,Double> request) {
		return ResponseEntity.ok(accountService.depositAmount(request.get("amount"),id));
	}
	
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDTO> withDrawAmount(@PathVariable long id, @RequestBody Map<String,Double> request) {
		return ResponseEntity.ok(accountService.withdrawAmount(id,request.get("amount")));
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts(@RequestHeader("Authorization") String token) {
		
		ResponseEntity<Map<String,Boolean>> response =  accountsFeign.authorizeUser(token);
		
			Map<String,Boolean> data = response.getBody();
			if(data.get("status") == false) {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		return ResponseEntity.ok(accountService.getAllAccounts());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable long id) {
		accountService.deleteAccountById(id);
		return ResponseEntity.ok("Account with id "+id+" deleted successfully!!!");
	}
}
