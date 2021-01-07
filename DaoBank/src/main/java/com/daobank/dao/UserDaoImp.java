package com.daobank.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daobank.ConnectionFactory;
import com.daobank.models.Account;
import com.daobank.models.User;

public class UserDaoImp implements UserDao {
	
	// SELECT
	@Override
	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM users;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("user_type").charAt(0)
						));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
		
	}
	
	public User selectUserById(int id) {
		List<User> users = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM users WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("user_type").charAt(0)
						));		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users.get(0);
		
	}
		
		
	public List<User> selectUsersByAccountId(int account_id){
		List<User> userList = new ArrayList();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "select * from user_accounts left join users on users.user_id = user_accounts.u_id where a_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, account_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userList.add(new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("user_type").charAt(0)
						));		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}return userList;
	}	
	
	public User selectUserByUsername(String username) {
		List<User> users = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM users WHERE username = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("pass"),
						rs.getString("user_type").charAt(0)
						));		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users.get(0);
		
	}
	
	//Insert
	public User insertUser(User u) {
		String mySQL = "INSERT INTO users (username, pass, user_type) values (?, ?, ?);";
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(mySQL);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassword());
			ps.setString(3, String.valueOf(u.getUserType()));
			ps.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return selectUserByUsername(u.getUserName());
	}
	
	//update
	public void updateUser(User u) {
		try(Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "UPDATE users SET username = ?, pass = ?, user_type = ? WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassword());
			ps.setString(3, String.valueOf(u.getUserType()));
			ps.setInt(4, u.getUserId());
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//delete
	public void deleteUser(User u) {
		Connection conn = ConnectionFactory.getConnection();
		String mySQL = "DELETE FROM users where user_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(mySQL);
			ps.setInt(1, u.getUserId());
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
