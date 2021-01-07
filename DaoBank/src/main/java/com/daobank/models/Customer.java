package com.daobank.models;

public class Customer extends User{
	
	public Customer(String username, String password) {
		super(username,password);
	}

	@Override
	public String toString() {
		return "Customer [getUserId()=" + getUserId() + ", getUserName()=" + getUserName() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
}
