package com.daobank.models;

public class Employee extends User{
	
	
	public Employee(String username, String password) {
		super(username,password);
	}

	@Override
	public String toString() {
		return "Employee [getUserId()=" + getUserId() + ", getUserName()=" + getUserName() + ", getPassword()="
				+ getPassword() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
