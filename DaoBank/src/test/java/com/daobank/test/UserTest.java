package com.daobank.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.daobank.dao.UserDao;
import com.daobank.dao.UserDaoImp;
import com.daobank.models.User;

public class UserTest {
	UserDao uDao = new UserDaoImp();

	
	@Test
	public void verifyAllUsersExists() {
		List<User> userList = uDao.selectAllUsers();
		assertNotNull(userList);
	}
	
	@Test
	public void verifyUserExists() {
		User user = new User("myTestUser", "testUserPassword", 'c');
		assertNotNull(user);
	}

	
	
}
