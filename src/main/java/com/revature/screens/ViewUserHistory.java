package com.revature.screens;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.daos.AdminDao;

public class ViewUserHistory implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private AdminDao ad = AdminDao.currentAdminDao;
	private ArrayList<String> userHistory = new ArrayList<String>();

	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		System.out.println("Please enter the user's username to view their transaction history: ");
		System.out.println("Enter 0 if you would like to exit.");
		String username = scan.nextLine();
		userHistory = ad.viewUserTransactionHistory(username);
		if(username.equals("0")) {
			return new AdminHomeScreen();
		}
		else if(userHistory == null) {
			System.out.println("This username doesn't exist.");
			return this;
		}
		System.out.println("Transaction History");
		System.out.println("----------------------------------");
		for(String e: userHistory) {
			System.out.println(e);
		}
		System.out.println("\nEnter 0 to exit.");
		System.out.println("Enter anything else to try another user.");
		String choice = scan.nextLine();
		if(choice.equals("0")) {
			return new AdminHomeScreen();
		}
		else
			return this;
	}

}
