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
	void depositYeet(double balance, double yeet, String username);
	void withdrawYeet(double balance, double yeet, String username);
	boolean addDepositHistory(double amount, int userId);
	boolean addWithdrawalHistory(double amount, int userId);
	ArrayList<String> getHistory(int user_id);
	double getBalance(String username);
	void updateUser(String username);
	//void deleteUser(User u);

}
