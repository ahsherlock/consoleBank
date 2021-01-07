package com.daobank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daobank.ConnectionFactory;
import com.daobank.models.Account;
import com.daobank.models.User;

public class AccountDaoImp implements AccountDao{
	
	
	public List<Account> selectAllAccounts(){
		List<Account> accounts = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accounts.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
		
	}
	public List<Account> selectAllApprovedAccounts(){
		List<Account> accounts = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts WHERE approved = true";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accounts.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	public List<Account> selectAllDeniedAccounts(){
		List<Account> accounts = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts WHERE approved = false";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accounts.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	public List<Account> selectAllPendingAccounts(){
		List<Account> accounts = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts WHERE approved IS NULL";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accounts.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	public List<Account> selectAccountsByUser(int user_id){
		List<Account> accountList = new ArrayList();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "select * from user_accounts left join accounts on accounts.account_id = user_accounts.a_id where u_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountList.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}return accountList;
	}
	
	public Account selectAccountById(int id) {
		List<Account> accountList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountList.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountList.get(0);
	}
	public Account selectAccountByAccountNumber(Long accountNumber) {
		List<Account> accountList = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_number = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountList.add(new Account(
						rs.getInt("account_id"),
						rs.getLong("account_number"),
						rs.getBoolean("approved"),
						rs.getDouble("balance")
						));		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountList.get(0);
	}
	
	
	//Insert
	public Account insertAccount(Account a) {
		String sql = "INSERT into accounts(account_number, approved, balance) values (?,?,?);";
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, a.getAccountNumber());
			if (a.getApproved() == null) {
	            ps.setNull(2, java.sql.Types.BOOLEAN);
	        } else {
	            ps.setBoolean(2, a.getApproved()); 
	        }
			ps.setDouble(3,a.getBalance());
			ps.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return selectAccountByAccountNumber(a.getAccountNumber());
	}
	//Insert
	public void insertUserIntoAccount(User u, Account a) {
		String sql = "INSERT into user_accounts(u_id, a_id) values (?,?);";
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUserId());
			ps.setInt(2,a.getAccountId());
			ps.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateAccount(Account a) {
		
		
	}
	//update
	public void updateAccountBalance(Account a) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, a.getBalance());
			ps.setInt(2,a.getAccountId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateAccountStatus(Account a) {
		
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE accounts SET approved = ? WHERE account_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, a.getApproved());
			ps.setInt(2,a.getAccountId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//delete
	public void deleteAccount(Account a) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "DELETE FROM accounts WHERE account_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getAccountId());
			ps.execute();
		}catch(SQLException e) {
			//e.printStackTrace();
		}
	}
	
	public boolean withdraw(Account a, Double withdrawAmount) {
		double accountBalance = a.getBalance();
		boolean success = false;
		if(withdrawAmount < 0 ) {
			System.out.println("Enter a positive number in order to complete your withdrawl..");
			success = false;
		}else if(accountBalance > withdrawAmount) {
			a.setBalance(accountBalance - withdrawAmount);
			updateAccountBalance(a);
			success = true;
		}else {
			System.out.println("Withdrawl failed");
		}
		return success;
		
		
	}
	public  boolean deposit(Account a, Double depositAmount) {
		if(depositAmount < 0) {
			System.out.println("Enter a positive number to continue with your deposit..");
			return false;
		}else {
			double accountBalance = a.getBalance();
			a.setBalance(accountBalance + depositAmount);
			updateAccountBalance(a);
			return true;
		}


	}
	public  boolean transfer(Account fromAccount, Double transferAmount, Long toAccountNumber) {
		System.out.println("Currently transfering :" + transferAmount);
		System.out.println("To account: "+ toAccountNumber + " with balance of: " + selectAccountByAccountNumber(toAccountNumber).getBalance());
		
		boolean withdrawSuccess = withdraw(fromAccount, transferAmount);
		boolean depositSuccess =  deposit(selectAccountByAccountNumber(toAccountNumber), transferAmount);
		boolean transferSuccess = false;
		if(withdrawSuccess && depositSuccess) {
			System.out.println("Transfer successful");
			transferSuccess = true;
		}
		System.out.println("Your new balance after the transfer is: " + fromAccount.getBalance());
		return transferSuccess;
		
		
	}
	
	
}
