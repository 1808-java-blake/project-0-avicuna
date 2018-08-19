package com.revature.screens;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class DisplayHistory implements Screen {
	
	private AppState state = AppState.state;
	private User currentUser = state.getCurrentUser();
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		int userId = currentUser.getUserId();
		System.out.println("Transaction History");
		System.out.println("----------------------------------");
		ArrayList<String> historyList = ud.getHistory(userId);
		for(String h: historyList) {
			System.out.println(h);
		}
		System.out.println("\nEnter 0 to exit.");
		String selection = scan.nextLine();
		if(selection.equals("0")) {
			return new HomeScreen();
		}
		System.out.println("Please press 0 to exit.");
		return this;
	}

}
