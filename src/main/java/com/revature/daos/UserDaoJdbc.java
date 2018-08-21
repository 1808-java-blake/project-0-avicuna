package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class UserDaoJdbc implements UserDao {
	
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
	public int createUser(User u) {	
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO users (username, pass, age, firstname, lastname) VALUES (?,?,?,?,?)",
					new String[] {"user_id"});
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getAge());
			ps.setString(4, u.getFirstName());
			ps.setString(5, u.getLastName());
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int userID = rs.getInt("user_id");
				log.trace("generated user_id is" + userID);
				createChecking(userID);
				createSavings(userID);
				return userID;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			}
		return 0;
	}
	
	public int createChecking(int user_id) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO accounts (account_type, balance, user_id) VALUES (?,?,?)",
					new String[] {"account_id"});
			ps.setString(1, "checking");
			ps.setInt(2, 0);
			ps.setInt(3, user_id);
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				log.trace("generated account_id is" + rs.getInt("account_id"));
				return rs.getInt("account_id");
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new checking account");
			}
		return 0;
	}
	
	public int createSavings(int user_id) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO accounts (account_type, balance, user_id) VALUES (?,?,?)",
					new String[] {"account_id"});
			ps.setString(1, "savings");
			ps.setInt(2, 0);
			ps.setInt(3, user_id);
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				log.trace("generated account_id is" + rs.getInt("account_id"));
				return rs.getInt("account_id");
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new savings account");
			}
		return 0;
	}
	
	

	@Override
	public User findByUsernameAndPassword(String username, String password) { 
		User u = new User();
		int id;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users WHERE username=? and pass=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
					
			if(rs.next()) {	
				id = rs.getInt("user_id");
				double balance = 0;
				PreparedStatement ps1 = conn.prepareStatement(
						"SELECT balance FROM accounts WHERE user_id=?");
				ps1.setInt(1, id);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {
					balance = rs1.getDouble("balance");
				}
				u.setAge(rs.getInt("age"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setUsername(rs.getString("username"));
				u.setUserId(id);
				u.setBalance(balance); 
				return u;
			} 
			
			else {
				log.warn("failed to find user with provided credentials from the db");
				return null;
			}
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean findUsername(String username) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				log.warn("the username is already in use.");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void depositYeet(double yeet, String username, String accountType) {
		double balance = 0;
		int userID = -1;
		if (accountType.equals("checking")) {
			balance = getCheckingBalance(username);
			System.out.println("Checking balance: " + balance);
		}
		else if (accountType.equals("savings")) {
			balance = getSavingsBalance(username);
			System.out.println("Savings balance: " + balance);
		}
		
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps0 = conn.prepareStatement(
					"SELECT * FROM users WHERE username=?");
			ps0.setString(1, username);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				userID = rs0.getInt("user_id");
				System.out.println("Here's my user_id: " + userID);
			}
			PreparedStatement ps = conn.prepareStatement(	
					"UPDATE accounts SET balance=? WHERE user_id=? AND account_type=?");
			
			balance += yeet;
			
			ps.setDouble(1, balance);
			ps.setInt(2, userID);
			ps.setString(3, accountType);
			ps.executeUpdate();
			System.out.println("My user ID: " + userID);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure");
		}
	}

	@Override
	public void withdrawYeet(double yeet, String username, String accountType) {
		double balance = 0;
		int userID = 0;
		if (accountType.equals("checking")) {
			balance = getCheckingBalance(username);
		}
		else if (accountType.equals("savings")) {
			balance = getSavingsBalance(username);
		}
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps0 = conn.prepareStatement(
					"SELECT user_id FROM users WHERE username=?");
			ps0.setString(1, username);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				userID = rs0.getInt("user_id");
			}
			PreparedStatement ps = conn.prepareStatement (
					"UPDATE accounts SET balance=? WHERE user_id=? AND account_type=?");
			
			balance -= yeet;
			
			ps.setDouble(1, balance);
			ps.setInt(2, userID);
			ps.setString(3, accountType);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure");
		}

	}
	
	@Override
	public boolean wireTransfer(double amount, String username, String accountType, String recipientUsername) {
		if(!findUsername(recipientUsername)) {
			return false;
		}
		depositYeet(amount, accountType, recipientUsername);
		withdrawYeet(amount, accountType, username);
		return true;
	}

	@Override
	public boolean addDepositHistory(double amount, int userId) { 
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(	//GOING TO ADD COLUMN NAME ACCOUNT TYPE
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
			PreparedStatement ps = conn.prepareStatement(	//GOING TO ADD COLUMN NAME ACCOUNT TYPE
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
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create transaction history");
			return false;
			}
		return true;
	}
	@Override
	public boolean addWireTransferHistory(double amount, String username) {
		int userId = 0;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps1 = conn.prepareStatement(
					"SELECT user_id FROM users WHERE username=?");
			ps1.setString(1, username);
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()) {
				userId = rs1.getInt("user_id");
			}
			PreparedStatement ps2 = conn.prepareStatement(	//GOING TO ADD COLUMN NAME ACCOUNT TYPE 
					"INSERT INTO transactions (transaction_type, transaction_amount, user_id) VALUES (?,?,?)",
					new String[] {"transaction_id"});
			ps2.setString(1, "Wire Transfer");
			ps2.setDouble(2, amount);
			ps2.setInt(3, userId);
			int recordsCreated = ps2.executeUpdate();
			log.trace(recordsCreated + " records created");
			
			ResultSet rs2 = ps2.getGeneratedKeys();
			if(rs2.next()) {
				log.trace("generated transaction_id is" + rs2.getInt("transaction_id"));
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create transaction history");
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
		
			while(rs.next()) {	//GOING TO ADD THE ACCOUNT TYPE 
				if(rs.getString("transaction_type").equals("Deposit")) {
					history = "Deposit: " + rs.getDouble("transaction_amount");
				}
				else if(rs.getString("transaction_type").equals("Withdrawal")){
					history = "Withdrawal: " + rs.getDouble("transaction_amount");
				}
				else
					history = "Wire Transfer: " + rs.getDouble("transaction_amount");
				
				historyList.add(history);
			}
			
			return historyList;
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
			return null;
			}
	}

	@Override
	public double getCheckingBalance(String username) {
		int userID = 0;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps0 = conn.prepareStatement(
					"SELECT user_id FROM users WHERE username=?");
			ps0.setString(1, username);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				userID = rs0.getInt("user_id");
			}
			
			PreparedStatement ps = conn.prepareStatement(	
					"SELECT * FROM accounts WHERE user_id=?");
		
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("account_type").equals("checking")) {
					return rs.getDouble("balance");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public double getSavingsBalance(String username) {
		int userID = 0;
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps0 = conn.prepareStatement(
					"SELECT user_id FROM users WHERE username=?");
			ps0.setString(1, username);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				userID = rs0.getInt("user_id");
			}
			
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM accounts WHERE user_id=?");
		
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("account_type").equals("savings")) {
					return rs.getDouble("balance");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}



}
