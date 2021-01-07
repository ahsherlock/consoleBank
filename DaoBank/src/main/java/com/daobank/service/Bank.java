package com.daobank.service;

import java.util.InputMismatchException;
import java.util.List;

import org.apache.log4j.Logger;

import com.daobank.UserInput;
import com.daobank.dao.AccountDao;
import com.daobank.dao.AccountDaoImp;
import com.daobank.dao.UserDao;
import com.daobank.dao.UserDaoImp;
import com.daobank.models.Account;
import com.daobank.models.User;
import com.daobank.Log;


public class Bank {
	
	
	
	
	
	private static UserDao uDao = new UserDaoImp();
	private static AccountDao aDao = new AccountDaoImp();
	private static Account currentAccount = new Account();
	private static User loggedInUser = new User();
	private static UserInput userInput = new UserInput();
	private static Log log = new Log();
	
	
	
	
		
	
		public String askNewUserName(){
			String name = "";
			System.out.println("Enter your desired user name?");
			name = userInput.scanString();
			return name;
		}


		
		public String askNewPass(){
			String password = "";
			System.out.println("Enter your desired password?");
			password = userInput.scanString();
			return password;
		}
		public String askUserName(){
			String name = "";
			System.out.println("Enter your user name?");
			name = userInput.scanString();
			return name;
		}
		
		public String askPass(){
			String password = "";
			System.out.println("Enter your password?");
			password = userInput.scanString();
			return password;
		}
		
		public double askInitialBalance(){
			double initBalance = 0;
			System.out.println("How much would you like to initially deposit?");
			try 
			{
				initBalance = userInput.scanDouble();
				if(initBalance < 0)
				{
					throw new InputMismatchException();
				}
			}catch(InputMismatchException e) {
				log.info("Please enter a valid non-negative dollar amount!");
				askInitialBalance();
			}
			
			return initBalance;
		}
		
		
		public String newUser() {
			String input ="";
			System.out.println("Is this a new user? Enter Yes or Y or No or N");
			input = UserInput.scanString();
			if(!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("Yes") || 
					input.equalsIgnoreCase("n") || input.equalsIgnoreCase("No")))
			{
				System.out.println("Please enter either Yes, Y, No, N!");
				input = newUser();
			}
			return input;
		}
		
		
		public boolean verifyUser(String username, String password) {
			User u;
			try {
				u = uDao.selectUserByUsername(username);
				if(u.getPassword().equals(password) == true) {
					Log.info("User has been validated in database");
				}else {
					Log.info("Invalid user creds entered");
				}
				return u.getPassword().equals(password);
			} catch (IndexOutOfBoundsException e) {
				Log.info("Username does not exist");
				//e.printStackTrace();
			}
			return false;
		
			
			
		}
	
	public void requestNewAccount(User loggedInUser) {
		System.out.println("\n------------------------------");
		System.out.println("Let us create you a safe place to store your hard earned money!");
		double initialAccountBalance = askInitialBalance();
		if(initialAccountBalance < 0) {
			System.out.println("There was an error with setting your initial balance. Please try again.");
			initialAccountBalance = askInitialBalance();
		}
		Account newInsertedAccount = new Account(initialAccountBalance);
		newInsertedAccount = aDao.insertAccount(newInsertedAccount);
		System.out.println("Your application for account with account number: " + newInsertedAccount.getAccountNumber() + "was created sucessfully");
		System.out.println("An Employee will review your application shortly!");
		Log.info("An account has been requested for user: " + loggedInUser.getUserName());
		aDao.insertUserIntoAccount(loggedInUser, newInsertedAccount);
		menus(loggedInUser);
	}
		
	public void customerAccounts(User u) {
		System.out.println("-------------------------------------");
		List<Account> accounts = aDao.selectAccountsByUser(u.getUserId());
		int accountSelector = 1;
		System.out.println("Please select an account.");
		System.out.println("0 ~ Back");
		for(Account a : accounts) {
			System.out.println(accountSelector + " - " + a.getAccountNumber() +" : " + String.valueOf(a.getBalance()));
			accountSelector++;
		}
		int userAccountSelection = UserInput.scanInt();
		if(userAccountSelection == 0) {
			menus(u);
			return;
		}else if(userAccountSelection > accounts.size() || userAccountSelection <= 0) {
			System.out.println("Invalid Input, please enter a valid choice.");
			customerAccounts(u);
		}
		viewAccount(accounts.get(userAccountSelection-1));
		
	}
	public void viewAccountEmployee() {
		
		
		
	}
	public void viewAccount(Account a) {
		if(a == null) {
			System.out.println("There was an error accessing your account");
			menus(loggedInUser);
		}
		if(!a.getApproved()) {
			System.out.println("Your account is still waiting on approval");
			menus(loggedInUser);
		}else {
			System.out.println("--------------------------------------------");
			System.out.println("Account Number - " + a.getAccountNumber());
			System.out.println("Total - " + a.getBalance());
			System.out.println("--------------------------------------------");
			System.out.println("Account Actions:");
			System.out.println("0 - Back");
			System.out.println("1 - Withdraw");
			System.out.println("2 - Deposit");
			System.out.println("3 - Transfer");
			String userMenuChoice = UserInput.scanString();
			switch(userMenuChoice) {
			case"0":
				customerAccounts(loggedInUser);
			case "1":
				withdraw(a);
				break;
			case "2":
				deposit(a);
				break;
			case "3":
				transfer(a);
				break;
			}
		
		}
		
	}
	public void viewAccountAdmin(Account a) {
		if(a == null) {
			System.out.println("There was an error accessing your account");
			menus(loggedInUser);
		}else {
			System.out.println("--------------------------------------------");
			System.out.println("Account Number - " + a.getAccountNumber());
			System.out.println("Total - " + a.getBalance());
			System.out.println("--------------------------------------------");
			System.out.println("Account Actions:");
			System.out.println("0 - Back");
			System.out.println("1 - Withdraw");
			System.out.println("2 - Deposit");
			System.out.println("3 - Transfer");
			System.out.println("4 - Approve Account");
			System.out.println("5 - Cancel Account");
			String userMenuChoice = UserInput.scanString();
			switch(userMenuChoice) {
			case "0":
				menus(loggedInUser);
				break;
			case "1":
				withdrawAdmin(a);
				break;
			case "2":
				depositAdmin(a);
				break;
			case "3":
				transferAdmin(a);
				break;
			case "4":
				adminApprove(a);
				break;
			case "5":
				adminDeleteAccount(a);
				break;
				
			}
		}
		
	}

	
	//----------------------------------------------- ADMIN FUNCTIONS
	public void withdrawAdmin(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter withdraw amount:");
		System.out.println("0 - Back");
		Double withdrawAmount = UserInput.scanDouble();
		if(withdrawAmount == 0) {
			viewAccountAdmin(account);
			return;
		}
		if (!aDao.withdraw(account, withdrawAmount)) {
			System.out.println("There was an Error with your transaction");
		}
		viewAccountAdmin(account);
	}
	
	public void depositAdmin(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter deposit amount:");
		System.out.println("0 - Back");
		Double depositAmount = UserInput.scanDouble();
		if(depositAmount == 0) {
			viewAccountAdmin(account);
			return;
		}
		if(!aDao.deposit(account, depositAmount)) {
			System.out.println("There was an error with your transaction.");
		}
		viewAccountAdmin(account);
	}
	
	public void transferAdmin(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter account number to transfer to.");
		Long toAccountNumber = UserInput.scanLong();
		System.out.println("Enter transfer amount.");
		Double transferAmount = UserInput.scanDouble();
		
		if(!aDao.transfer(account, transferAmount, toAccountNumber)) {
			System.out.println("There was an error with your transaction.");
		}
		viewAccountAdmin(account);
	}
	
	public void adminApprove(Account a) {
		System.out.println(" Approving account with account number: " + a.getAccountNumber());
		a.setApproved(true);
		aDao.updateAccountStatus(a);
		System.out.println(" Account status is now: " + a.getApproved());
		menus(loggedInUser);
	}
	public void adminDeleteAccount(Account a) {
		System.out.println("Deleting account with account number: "+  a.getAccountNumber());
		aDao.deleteAccount(a);
		System.out.println("Deleted account.");
		menus(loggedInUser);
	}
	
	
	
	public void withdraw(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter withdraw amount:");
		System.out.println("0 - Back");
		Double withdrawAmount = UserInput.scanDouble();
		if(withdrawAmount == 0) {
			viewAccount(account);
			return;
		}
		if (!aDao.withdraw(account, withdrawAmount)) {
			System.out.println("There was an Error with your transaction");
		}
		viewAccount(account);
	}
	
	public void deposit(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter deposit amount:");
		System.out.println("0 - Back");
		Double depositAmount = UserInput.scanDouble();
		if(depositAmount == 0) {
			viewAccount(account);
			return;
		}
		if(!aDao.deposit(account, depositAmount)) {
			System.out.println("There was an error with your transaction.");
		}
		viewAccount(account);
	}
	
	public void transfer(Account account) {
		System.out.println("--------------------------------------------");
		System.out.println("Enter account number to transfer to.");
		Long toAccountNumber = UserInput.scanLong();
		System.out.println("Enter transfer amount.");
		Double transferAmount = UserInput.scanDouble();
		
		if(!aDao.transfer(account, transferAmount, toAccountNumber)) {
			System.out.println("There was an error with your transaction.");
		}
		viewAccount(account);
	}
	
	
	public void addExistingUser(User u) {
		System.out.println("-------------------------------");
		System.out.println("Enter the account number that you wish to add");
		Long accountNumber = UserInput.scanLong();
		aDao.insertUserIntoAccount(u, aDao.selectAccountByAccountNumber(accountNumber));
		System.out.println("The user: "+ u.getUserName()+ "has been added to the account: " + accountNumber );
		menus(loggedInUser);
	}
	
	public void viewAllUsers() {
		System.out.println("---------------------------");
		System.out.println("Here are all of the users.");
		List<User> userList = uDao.selectAllUsers();
		for(User u : userList) {
			System.out.println("------------- \n");
			System.out.println("User ID: " + u.getUserId());
			System.out.println("Username: " + u.getUserName());
			System.out.println("User Type: " + u.getUserType() + "\n");
		}

		menus(loggedInUser);
	}
	
	public void viewAllAccounts() {
		System.out.println("---------------------");
		System.out.println("Here is a list of all accounts");
		List<Account> accountsList = aDao.selectAllAccounts();
		for(Account a : accountsList) {
			System.out.println("------------- \n");
			System.out.println("Account ID: " + a.getAccountId());
			System.out.println("Account Number: " + a.getAccountNumber());
			System.out.println("Account Status: " + a.getApproved());
			System.out.println("Account Balance: " + a.getBalance() +  "\n");
		}

		menus(loggedInUser);
	}
	public void viewAllPendingAccounts() {
		System.out.println("---------------------");
		System.out.println("Here is a list of all the accounts that are pending accounts");
		List<Account> accountsList = aDao.selectAllPendingAccounts();
		for(Account a : accountsList) {
			System.out.println("------------- \n");
			System.out.println("Account ID: " + a.getAccountId());
			System.out.println("Account Number: " + a.getAccountNumber());
			System.out.println("Account Status: " + a.getApproved());
			System.out.println("Account Balance: " + a.getBalance() +  "\n");
		}
		System.out.println("To approve account, please enter account ID or 0 to exit");
		int accountIdChoice = UserInput.scanInt();
		if(accountIdChoice == 0) {
			menus(loggedInUser);
		}
		Account chosenAccount = aDao.selectAccountById(accountIdChoice);
		System.out.println("account: "+chosenAccount.getAccountNumber()+" has been selected to be aproved");
		chosenAccount.setApproved(true);
		aDao.updateAccountStatus(chosenAccount);
		System.out.println("account: "+chosenAccount.getAccountNumber()+" has been approved and database has been updated \n");
		System.out.println("Account number : " + chosenAccount.getAccountNumber() + " status is now: " + chosenAccount.getApproved());
		menus(loggedInUser);
	}
	public void selectUserByUsername() {
		System.out.println("Please enter the username of the user that you wish to find: ");
		String userChoice = UserInput.scanString();
		User chosenUser = uDao.selectUserByUsername(userChoice);
		System.out.println("\nUser with username: "+chosenUser.getUserName()+ "has been accessed from the database\n");
		List<Account> userAccounts = aDao.selectAccountsByUser(chosenUser.getUserId());
		System.out.println("Here is information for user : " + chosenUser.getUserName());
		System.out.println("Username: "+ chosenUser.getUserName());
		System.out.println("User Id :"+ chosenUser.getUserId());
		System.out.println("User Accounts: ");
		for(Account a: userAccounts) {
			System.out.println("------------- \n");
			System.out.println("Account ID: " + a.getAccountId());
			System.out.println("Account Number: " + a.getAccountNumber());
			System.out.println("Account Status: " + a.getApproved());
			System.out.println("Account Balance: " + a.getBalance() +  "\n");
		}
		menus(loggedInUser);
	}
	public void selectAccountByAccountNumber() {
		System.out.println("Please enter the account number of the account that you wish to find: ");
		Long userChoice = UserInput.scanLong();
		Account chosenAccount = aDao.selectAccountByAccountNumber(userChoice);
		System.out.println("\nAccount with account number: " + chosenAccount.getAccountNumber() + "has been accessed from the database.\n");
		System.out.println("----------------------------------------- \n");
		System.out.println("Here is information for Account Number :" + chosenAccount.getAccountNumber());
		System.out.println("Account ID: "+ chosenAccount.getAccountId());
		System.out.println("Account Balance :"+ chosenAccount.getBalance());
		System.out.println("Approved Status: " + chosenAccount.getApproved());
		List<User> usersOfAccount = uDao.selectUsersByAccountId(chosenAccount.getAccountId());
		System.out.println("Owners of this account: ");
		for(User u : usersOfAccount) {
			System.out.println("Username: "+ u.getUserName());
		}
		System.out.println("------------------------------------------- \n");
		menus(loggedInUser);
	}
	public void selectAccountByAccountNumberAdmin() {
		System.out.println("Please enter the account number of the account that you wish to find: ");
		Long userChoice = UserInput.scanLong();
		Account chosenAccount = aDao.selectAccountByAccountNumber(userChoice);
		log.info("\nAccount with account number: " + chosenAccount.getAccountNumber() + "has been accessed from the database.\n");
		viewAccountAdmin(chosenAccount);
	}
	
	public void login() {
			System.out.println("WELCOME TO      || T H E    B A N K ||");
			String newUserInput = newUser();
			if(newUserInput.equalsIgnoreCase("y") || newUserInput.equalsIgnoreCase("yes")) {
				System.out.println("Hello new friend! Lets get you set up with a user log in to our system!");
				String username = askNewUserName();
				String password = askNewPass();
				List<User> userList = uDao.selectAllUsers();
				for(User u : userList) {
					if(username == u.getUserName()) {
						System.out.println("That user already exists. Enter a different username");
						login();
					}
				}
				User newUser = new User(username, password, 'c');
				uDao.insertUser(newUser);
				System.out.println("YOUR ACCOUNT HAS BEEN CREATED! IT'S TIME TO GET EXCITED!!");
				log.info("\nA new user has been created and added to the database\n");
				login();

			}else if(newUserInput.equalsIgnoreCase("n") || newUserInput.equalsIgnoreCase("No")) {
				String customerUsername = askUserName();
				String customerPassword = askPass();
				if(verifyUser(customerUsername, customerPassword)) {
					loggedInUser = uDao.selectUserByUsername(customerUsername);
					menus(loggedInUser);
				}else {
					System.out.println("You entered the wrong information. Try again.");
					login();
				}
				
			}	

	}
	public void menus(User loggedInUser) {

		if (loggedInUser.getUserType() == 'c') {
			System.out.println("User named: "+ loggedInUser.getUserName() + " with user priv: " + loggedInUser.getUserType() + " has logged in");
			System.out.println("What would you like to do today??(Enter the number of your choice)");
			System.out.println("0	~ Log Out");
			System.out.println("1	~ View your accounts");
			System.out.println("2	~ Apply for a new account");
			System.out.println("3	~ Add an existing account");
			String userMenuChoice = UserInput.scanString();
			switch(userMenuChoice) {
			case "0":
				logout();
				break;
			case "1":
				customerAccounts(loggedInUser);
				break;
			case "2":
				requestNewAccount(loggedInUser);
				break;
			case "3":
				addExistingUser(loggedInUser);
				break;
			default:
				System.out.println("Invalid Input please try again");
				menus(loggedInUser);	
			}
		}else if (loggedInUser.getUserType() == 'e') {
			System.out.println("User named: "+ loggedInUser.getUserName() + " with user priv: " + loggedInUser.getUserType() + " has logged in\n");
			System.out.println("Hello employee " + loggedInUser.getUserName());
			System.out.println("What would you like to do today??(Enter the number of your choice)");
			System.out.println("0 	~ Log Out");
			System.out.println("1 	~ View All Customers");
			System.out.println("2 	~ View All Accounts");
			System.out.println("3 	~ Search for User by Username");
			System.out.println("4 	~ Search for Account by Account number");
			System.out.println("5 	~ View All Pending Accounts");
			String userMenuChoice = UserInput.scanString();
			switch(userMenuChoice) {
			case "0":
				logout();
				break;
			case "1":
				viewAllUsers();
				break;
			case "2":
				viewAllAccounts();
				break;
			case "3":
				selectUserByUsername();
				break;
			case "4":
				selectAccountByAccountNumber();
				break;
			case "5":
				viewAllPendingAccounts();
				break;
			default:
				System.out.println("Invalid Input please try again");
				menus(loggedInUser);	
			}
			
			
			
		}else if(loggedInUser.getUserType() == 'a') {
			System.out.println("User named: "+ loggedInUser.getUserName() + " with user priv: " + loggedInUser.getUserType() + " has logged in");
			System.out.println("Hello Admin " + loggedInUser.getUserName());
			System.out.println("What would you like to do today??(Enter the number of your choice)");
			System.out.println("0 	~ Log Out");
			System.out.println("1 	~ View All Customers");
			System.out.println("2 	~ View All Accounts");
			System.out.println("3 	~ Search for User by Username");
			System.out.println("4 	~ Search for Account by Account number");
			System.out.println("5 	~ View All Pending Accounts");
			String userMenuChoice = UserInput.scanString();
			switch(userMenuChoice) {
			case "0":
				logout();
				break;
			case "1":
				viewAllUsers();
				break;
			case "2":
				viewAllAccounts();
				break;
			case "3":
				selectUserByUsername();
				break;
			case "4":
				selectAccountByAccountNumberAdmin();
				break;
			case "5":
				viewAllPendingAccounts();
				break;
			default:
				System.out.println("Invalid Input please try again");
				menus(loggedInUser);	
			}
			
		}
	}
	public void logout() {
		loggedInUser = null;
		start();
	}
	
	public void start() {
		login();
	
		
		
		/* Testing code =]
		 * List<User> uList = uDao.selectAllUsers();
		System.out.println(uList);
		User me = uDao.insertUser(new User("me", "pass", "customer"));
		List<User> uList2 = uDao.selectAllUsers();
		System.out.println(uList2);			
		System.out.println("All Approved Accounts");
		List<Account> accountList = aDao.selectAllApprovedAccounts();
		System.out.println(accountList);	
		System.out.println("All Denied Accounts");
		List<Account> deniedAccounts = aDao.selectAllDeniedAccounts();
		System.out.println(deniedAccounts);
		System.out.println("All Pending Accounts");
	
		System.out.println("The account id1 =" + aDao.selectAccountById(1));	
		System.out.println("The current account is: ");
		currentAccount = aDao.selectAccountById(1);
		System.out.println(currentAccount);
		System.out.println("This should be the same account: " + aDao.selectAccountByAccountNumber(currentAccount.getAccountNumber()));	*/

	}
	
}
