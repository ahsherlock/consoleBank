package com.daobank.dao;

import java.util.List;

import com.daobank.models.Account;
import com.daobank.models.User;



public interface AccountDao {
	public List<Account> selectAllAccounts();
	public List<Account> selectAllApprovedAccounts();
	public List<Account> selectAllDeniedAccounts();
	public List<Account> selectAllPendingAccounts();
	public List<Account> selectAccountsByUser(int user_id);
	
	public Account selectAccountById(int id);
	public Account selectAccountByAccountNumber(Long accountNumber);
	
	
	
	public  boolean withdraw(Account a, Double withdrawAmount);
	public  boolean deposit(Account a, Double depositAmount);
	public  boolean transfer(Account fromAccount, Double transferAmount, Long toAccountNumber);
	
	//Insert
	public Account insertAccount(Account a);
	public void insertUserIntoAccount(User u, Account a);
	
	//update
	public void updateAccount(Account a);
	public void updateAccountBalance(Account a);
	public void updateAccountStatus(Account a);
	
	//delete
	public void deleteAccount(Account a);
}
