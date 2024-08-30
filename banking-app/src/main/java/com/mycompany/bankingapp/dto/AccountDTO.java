package com.mycompany.bankingapp.dto;




public class AccountDTO {
	public Long id;
	public String accountHolderName;
	public double accountBalance;
	
	public AccountDTO() {};
	
	public AccountDTO(Long id, String accountHolderName, double accountBalance) {
		super();
		this.id = id;
		this.accountHolderName = accountHolderName;
		this.accountBalance = accountBalance;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	
	
}
