package com.revature.util;

import com.revature.beans.Admin;
import com.revature.beans.User;

public class AppState {
	public static final AppState state = new AppState();
	private User currentUser;
	private Admin currentAdmin;

	private AppState() {

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Admin getCurrentAdmin() {
		return currentAdmin;
	}
	
	public void setCurrentAdmin(Admin currentAdmin) {
		this.currentAdmin = currentAdmin;
	}
}
