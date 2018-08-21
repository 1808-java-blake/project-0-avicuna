package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class DisplayBalance implements Screen {
	
	private AppState state = AppState.state;
	private User currentUser = state.getCurrentUser();
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		String username = currentUser.getUsername();
		System.out.println("Current Balance");
		System.out.println("--------------------------");
		System.out.println("Checking: " + ud.getCheckingBalance(username));
		System.out.println("Savings: " + ud.getSavingsBalance(username) + "\n");
		System.out.println("Enter 0 to exit.");
		String selection = scan.nextLine();
		if(selection.equals("0")) {
			return new HomeScreen();
		}
		System.out.println("Please press 0 to exit.");
		return this;
	}

}
