package com.revature.daos;

import java.awt.List;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.AppState;
import com.revature.util.ConnectionUtil;

public class UserDaoJdbc implements UserDao {
	
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	@Override
	public int createUser(User u) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO users (username, pass, age, firstname, lastname, history, balance) VALUES (?,?,?,?,?,?,?)",
					new String[] {"user_id"});
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getAge());
			ps.setString(4, u.getFirstName());
			ps.setString(5, u.getLastName());
			String[] noHistory = new String[] {};
			Array sqlArray = conn.createArrayOf("CHAR", noHistory);
			ps.setArray(6, sqlArray);
			ps.setFloat(7, 0);
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				log.trace("generated user_id is" + rs.getInt("user_id"));
				return rs.getInt("user_id");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			}
		return 0;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		try (Connection conn = cu.getConnection()) {
			// don't do this
//			Statement s = conn.createStatement();
//			ResultSet rs = s.executeQuery("SELECT * FROM app_users WHERE username='" + 
//							username + "' AND pass='" + password + "'");
			
			// do this
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users WHERE username=? and pass=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
					
			if(rs.next()) {
				User u = new User();
				u.setAge(rs.getInt("age"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setUsername(rs.getString("username"));
				u.setUserId(rs.getInt("user_id"));
				u.setBalance(rs.getDouble("balance"));
				return u;
			} else {
				log.warn("failed to find user with provided credentials from the db");
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean findUsername(String username) {
//		try (Connection conn = cu.getConnection()) {
//			// don't do this
////			Statement s = conn.createStatement();
////			ResultSet rs = s.executeQuery("SELECT * FROM app_users WHERE username='" + 
////							username + "' AND pass='" + password + "'");
//			
//			// do this
//			PreparedStatement ps = conn.prepareStatement(
//					"SELECT * FROM app_users WHERE username=? and pass=?");
//			ps.setString(1, username);
//			ResultSet rs = ps.executeQuery();
//					
//			if(rs.next()) {
//				User u = new User();
//				u.setAge(rs.getInt("age"));
//				u.setFirstName(rs.getString("firstname"));
//				u.setLastName(rs.getString("lastname"));
//				u.setUsername(rs.getString("username"));
//				u.setUserId(rs.getInt("user_id"));
//				return u;
//			} else {
//				log.warn("failed to find user with provided credentials from the db");
//				return null;
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		return false;
	}

	@Override
	public void depositYeet(double balance, double yeet, String username) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE users SET balance=? WHERE username=?");
			
			balance += yeet;
			
			ps.setDouble(1, balance);
			ps.setString(2, username);
			int recordsCreated = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failure");
		}
	}

	@Override
	public void withdrawYeet(double balance, double yeet, String username) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE users SET balance=? WHERE username=?");
			
			balance -= yeet;
			
			ps.setDouble(1, balance);
			ps.setString(2, username);
			int recordsCreated = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failure");
		}

	}

	@Override
	public boolean addDepositHistory(double amount, int userId) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO transactions (transaction_type, transaction_amount, user_id) VALUES (?,?,?)",
					new String[] {"transaction_id"});
			ps.setString(1, "Deposit");
			ps.setDouble(2, amount);
			ps.setInt(3, userId);
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				log.trace("generated transaction_id is" + rs.getInt("transaction_id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			return false;
			}
		return true;
	}
	
	public boolean addWithdrawalHistory(double amount, int userId) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO transactions (transaction_type, transaction_amount, user_id) VALUES (?,?,?)",
					new String[] {"transaction_id"});
			ps.setString(1, "Withdrawal");
			ps.setDouble(2, amount);
			ps.setInt(3, userId);
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				log.trace("generated transaction_id is" + rs.getInt("transaction_id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			return false;
			}
		return true;
	}

	@Override
	public ArrayList<String> getHistory(int userId) {
		ArrayList<String> historyList = new ArrayList<String>();
		String history = new String();
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM transactions WHERE user_id=?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
		
			while(rs.next()) {
				if(rs.getString("transaction_type").equals("Deposit")) {
					history = "Deposit: " + rs.getDouble("transaction_amount");
				}
				else {
					history = "Withdrawal: " + rs.getDouble("transaction_amount");
				}
				historyList.add(history);
			}
			
			return historyList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			return null;
			}
	}

	@Override
	public double getBalance(String username) {
		// TODO Auto-generated method stub
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT balance FROM users WHERE username=?");
		
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getDouble("balance");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void updateUser(String username) {
		// TODO Auto-generated method stub
		
	}

}