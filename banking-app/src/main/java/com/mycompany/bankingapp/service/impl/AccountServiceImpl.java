package com.mycompany.bankingapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.bankingapp.dto.AccountDTO;
import com.mycompany.bankingapp.entity.Account;
import com.mycompany.bankingapp.exception.AccountNotFoundException;
import com.mycompany.bankingapp.repository.AccountsRepository;
import com.mycompany.bankingapp.service.AccountService;
import com.mycompany.bankingapp.utility.AccountMapper;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	public AccountsRepository repo;
	
//	public AccountServiceImpl(AccountsRepository repo) {
//		this.repo = repo;
//	}
	
	@Override
	public AccountDTO createNewAccount(AccountDTO acc) {
		// TODO Auto-generated method stub
		Account accToBeSaved = AccountMapper.mapToAccount(acc);
		Account dataResponse = repo.save(accToBeSaved);
		return AccountMapper.mapToAccountDTO(dataResponse);
	}

	@Override
	public AccountDTO getAccountByID(long id) {
		// TODO Auto-generated method stub
		Account dataResponse = repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with account number-"+id+" not found!!!"));
		return AccountMapper.mapToAccountDTO(dataResponse);
	}

	@Override
	public AccountDTO depositAmount(double amount,long id) {
		// TODO Auto-generated method stub
		Account dataResponse = repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with account number-"+id+" not found!!!"));
		dataResponse.setAccountBalance(dataResponse.getAccountBalance()+amount);
		Account updatedAccount = repo.save(dataResponse);
		return AccountMapper.mapToAccountDTO(updatedAccount);
	}

	@Override
	public AccountDTO withdrawAmount(long id, double amount) {
		// TODO Auto-generated method stub
		Account dataResponse = repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with account number-"+id+" not found!!!"));
		if(dataResponse.getAccountBalance() < amount) {
			throw new RuntimeException("Insufficient Funds!!!");
		}
		dataResponse.setAccountBalance(dataResponse.getAccountBalance() - amount);
		Account updatedAcc = repo.save(dataResponse);
		return AccountMapper.mapToAccountDTO(updatedAcc);
	}

	@Override
	public List<AccountDTO> getAllAccounts() {
		// TODO Auto-generated method stub
		List<Account> allAccounts = repo.findAll();
		List<AccountDTO> res = new ArrayList<>();
		for(Account acc : allAccounts) {
			res.add(new AccountDTO(acc.getId(),acc.getAccountHolderName(),acc.getAccountBalance()));
		}
		return res;
	}

	@Override
	public void deleteAccountById(long id) {
		// TODO Auto-generated method stub
		repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with account number-"+id+" not found!!!"));
		repo.deleteById(id);
	};

}
