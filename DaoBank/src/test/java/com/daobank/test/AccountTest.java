package com.daobank.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.daobank.models.Account;
import com.daobank.models.User;
import com.daobank.dao.AccountDao;
import com.daobank.dao.AccountDaoImp;
import com.daobank.dao.UserDao;
import com.daobank.dao.UserDaoImp;
import com.daobank.models.Account;


public class AccountTest {
	AccountDao aDao = new AccountDaoImp();
	
	@Test
	public void verifyAccountExists() {
		Account account = new Account(50000.00);
		assertNotNull(account);
	}
	@Test
	public void verifyAccountPending() {	
		List<Account> pendingAccounts = aDao.selectAllPendingAccounts();
		assertNotNull(pendingAccounts);
	}
	@Test
	public void verifyAccountApproved() {
		List<Account> approvedAccounts = aDao.selectAllApprovedAccounts();
		assertNotNull(approvedAccounts);
	}
	@Test
	public void verifyAccountDenied() {
		List<Account> approvedAccounts = aDao.selectAllDeniedAccounts();
		assertNotNull(approvedAccounts);
	}
	
}
	


