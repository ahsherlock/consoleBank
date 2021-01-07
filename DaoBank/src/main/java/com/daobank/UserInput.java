package com.daobank;

import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.util.Scanner;



public class UserInput {
	
	private static Scanner scanner = null;
	private static DecimalFormat df = new DecimalFormat("0.00");
	private static BufferedReader reader = null;
	
	public static String next() {
		checkScanner();
		String message = scanner.next();
		Log.info(" -- User input -- " + message);
		return message;
	}
	
	public static void sysout(String message) {
		checkScanner();
		Log.info(" -- System message -- " + message);
		System.out.println(message);
	}

	public static String scanString() {
		checkScanner();
		return scanner.next();
	}
	public static int scanInt() {
		checkScanner();
		return scanner.nextInt();
	}
	public static double scanDouble() {
		checkScanner();
		return scanner.nextDouble();
	}
	public static long scanLong() {
		checkScanner();
		return scanner.nextLong();
	}

	
	private static void checkScanner() {
		if(scanner == null ) {
			scanner = new Scanner(System.in);
		}
	}

		
	
}
