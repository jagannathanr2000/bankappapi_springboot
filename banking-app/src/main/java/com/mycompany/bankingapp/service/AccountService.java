package com.mycompany.bankingapp.service;

import java.util.List;

import com.mycompany.bankingapp.dto.AccountDTO;

public interface AccountService {
	public AccountDTO createNewAccount(AccountDTO acc);
	public AccountDTO getAccountByID(long id);
	public AccountDTO depositAmount(double amount, long id);
	public AccountDTO withdrawAmount(long id, double amount);
	public List<AccountDTO> getAllAccounts();
	public void deleteAccountById(long id);
}
