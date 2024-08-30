package com.mycompany.bankingapp.utility;

import com.mycompany.bankingapp.dto.AccountDTO;
import com.mycompany.bankingapp.entity.Account;

public class AccountMapper {
	
	public static Account mapToAccount(AccountDTO acc) {
		Account newAcc = new Account(acc.getId(),acc.getAccountHolderName(),acc.getAccountBalance());
	
		return newAcc;
	}
	
	public static AccountDTO mapToAccountDTO(Account acc) {
		
		AccountDTO newAccDto = new AccountDTO(acc.getId(),acc.getAccountHolderName(),acc.getAccountBalance());
		return newAccDto;
	}
}
