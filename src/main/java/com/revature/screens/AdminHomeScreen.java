package com.revature.screens;

import java.util.Scanner;

public class AdminHomeScreen implements Screen {
	
	private Scanner scan = new Scanner(System.in);


	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		System.out.println("Please choose from the following options: ");
		System.out.println("Enter 1 to view users");
		System.out.println("Enter 2 to get user transaction info");
		System.out.println("Enter 3 to logout");
		String selection = scan.nextLine();
		switch (selection) {
		case "1":
			return new ViewAllUsers();
		case "2":
			return new ViewUserHistory();
		case "3":
			System.out.println("You've been logged out.");
			return new StartScreen();
		default:
			break;
		}
		return this;
	}

}
