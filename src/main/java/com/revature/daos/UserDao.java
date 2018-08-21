package com.revature.daos;

import java.util.ArrayList;

import com.revature.beans.User;

public interface UserDao {
	
public static final UserDao currentUserDao = new UserDaoJdbc();
	
	/**
	 * Takes in a user object and will persist that user
	 * 
	 * @param u
	 * @return 
	 */
	int createUser(User u);
	User findByUsernameAndPassword(String username, String password);
	boolean findUsername(String username);
	void depositYeet(double yeet, String username, String accountType);
	void withdrawYeet(double yeet, String username, String accountType);
	boolean wireTransfer(double amount, String username, String accountType, String recipientUsername);
	boolean addDepositHistory(double amount, int userId);
	boolean addWithdrawalHistory(double amount, int userId);
	boolean addWireTransferHistory(double amount, String username);
	ArrayList<String> getHistory(int user_id);
	double getCheckingBalance(String username);
	double getSavingsBalance(String username);
}
