package com.daobank.models;

import java.util.ArrayList;
import java.util.List;
import com.daobank.models.Account;


public class User {
		
	private int userId;
	private String userName;
	private String password;
	private char userType;
	private List<Account> accounts;
	
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public User(String userName, String password, char userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}
	
	public User(int userId, String userName, String password, char userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		accounts = new ArrayList<Account>();
	
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", accounts=" + accounts
				+ "]";
	}
	public char getUserType() {
		// TODO Auto-generated method stub
		return userType;
	}
	public void setUserType(char userType) {
		this.userType = userType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;


		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	
}
