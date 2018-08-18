package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;

public class HomeScreen implements Screen {
	private User current;

	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
//	private User u = UserSerializer.getCurrentUser();
	

	public Screen start() {
		System.out.println("Please choose from following options: ");
		System.out.println("Enter 1 to deposit YeetCoin");
		System.out.println("Enter 2 to withdraw YeetCoin");
		System.out.println("Enter 3 to view balance");
		System.out.println("Enter 4 to view transaction history");
		System.out.println("Enter 5 to logout");
		String selection = scan.nextLine();
		switch (selection) {
		case "1":
			return new DepositScreen();
		case "2":
			return new WithdrawScreen();
		case "3":
			return new DisplayBalance();
		case "4":
			return new DisplayHistory();
		case "5":
			System.out.println("You've been logged out");
			return new StartScreen();
		default:
			break;
		}

		return this;
	}
}
