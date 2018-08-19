package com.revature.daos;

import java.util.ArrayList;

import com.revature.beans.Admin;
import com.revature.beans.User;

public interface AdminDao {
	public static final AdminDao currentAdminDao = new AdminDaoJdbc();
	
	Admin findByUsernameAndPassword(String username, String password);
	ArrayList<User> viewAllUsers();
	ArrayList<String> viewUserTransactionHistory(String username);
}
