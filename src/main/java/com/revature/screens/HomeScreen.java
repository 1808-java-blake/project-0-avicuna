package com.revature.screens;

import java.util.Scanner;

public class HomeScreen implements Screen {

	private Scanner scan = new Scanner(System.in);
	
	public Screen start() {
		System.out.println("Please choose from following options: ");
		System.out.println("Enter 1 to deposit YeetCoin");
		System.out.println("Enter 2 to withdraw YeetCoin");
		System.out.println("Enter 3 to view balance");
		System.out.println("Enter 4 to view transaction history");
		System.out.println("Enter 5 to begin a wire transfer");
		System.out.println("Enter 6 to logout");
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
			return new WireTransfer();
		case "6":
			System.out.println("You've been logged out");
			return new StartScreen();
		default:
			break;
		}

		return this;
	}
}
