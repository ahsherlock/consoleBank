package com.daobank.models;

import java.util.Random;

public class Account {
	private int accountId;
	private Long accountNumber;
	private Boolean approved;
	private Double balance;
	
	
	
	public Account(int accountId, Long accountNumber, Boolean approved, Double balance) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.approved = approved;
		this.balance = balance;
	}
	public Account(Double balance) {
		super();
		this.balance = balance;
		this.accountNumber = new Random().nextLong();
		if(this.accountNumber<0) {
			this.accountNumber *= -1;
		}
		this.approved = null;
	}
	public Account() {
		
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountNumber=" + accountNumber + ", approved=" + approved
				+ ", balance=" + balance + "]";
	}
	
	
}
