package com.daobank;

import org.apache.log4j.BasicConfigurator;

import com.daobank.service.Bank;

public class Driver {
	
	private static Bank bank = new Bank();
	
	
	public static void main(String args[]) {
		BasicConfigurator.configure();
		bank.start();
	}

}
