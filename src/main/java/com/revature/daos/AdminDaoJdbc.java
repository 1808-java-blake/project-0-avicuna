package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Admin;
import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class AdminDaoJdbc implements AdminDao {
	
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Admin findByUsernameAndPassword(String username, String password) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM admins WHERE admin_username=? and admin_password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
					
			if(rs.next()) {
				Admin a = new Admin();
				a.setFirstName(rs.getString("admin_first_name"));
				a.setLastName(rs.getString("admin_last_name"));
				a.setUsername(rs.getString("admin_username"));
				a.setAdminId(rs.getInt("admin_id"));
				return a;
			} else {
				log.warn("failed to find admin with provided credentials from the db");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<User> viewAllUsers() { 
		ArrayList<User> listUsers = new ArrayList<User>();
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users");
			ResultSet rs = ps.executeQuery();
					
			while(rs.next()) {
				User u = new User();
				u.setAge(rs.getInt("age"));	
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setUsername(rs.getString("username"));
				u.setUserId(rs.getInt("user_id"));
//				u.setBalance(rs.getDouble("balance"));
				listUsers.add(u);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listUsers;
	}

	@Override
	public ArrayList<String> viewUserTransactionHistory(String username) {
		ArrayList<String> historyList = new ArrayList<String>();
		String history = new String();
		int thisUserId = 0;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps1 = conn.prepareStatement(
					"SELECT username, user_id FROM users");
			ResultSet rs = ps1.executeQuery();
					
			while(rs.next()) {
				if(rs.getString("username").equals(username)) {
					thisUserId = rs.getInt("user_id");
				}
			} 
			PreparedStatement ps2 = conn.prepareStatement(
					"SELECT * FROM transactions WHERE user_id=?");
			ps2.setInt(1, thisUserId);
			ResultSet rs2 = ps2.executeQuery();
		
			while(rs2.next()) {
				if(rs2.getString("transaction_type").equals("Deposit")) {
					history = "Deposit: " + rs2.getDouble("transaction_amount");
				}
				else if (rs2.getString("transaction_type").equals("Withdrawal")) {
					history = "Withdrawal: " + rs2.getDouble("transaction_amount");
				}
				else {
					history = "Wire Transfer: " + rs2.getDouble("transaction_amount");
				}
				historyList.add(history);
			}
			if(thisUserId == 0) {
				return null;
			}
			
			return historyList;
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to get user transaction info");
			return null;
			}

	}

}
