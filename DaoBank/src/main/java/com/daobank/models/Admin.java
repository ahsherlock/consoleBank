package com.daobank.models;

public class Admin extends User {
	public Admin(String username, String password) {
		super(username,password);
	}

	@Override
	public String toString() {
		return "Admin [getUserId()=" + getUserId() + ", getUserName()=" + getUserName() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
	
}
