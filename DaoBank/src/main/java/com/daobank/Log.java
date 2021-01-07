package com.daobank;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;



public class Log {
	
final static Logger loggy = Logger.getLogger(Driver.class);
	
	public static void startLogger() {
		loggy.setLevel(Level.ALL);
		loggy.info("This is an info block!");
		
		loggy.info("The customer has started their online banking process");
	}
	
	public static void info(String message) {
		loggy.info(message);
	}
	public static void warn(String message) {
		loggy.warn(message);
	}
	
}
