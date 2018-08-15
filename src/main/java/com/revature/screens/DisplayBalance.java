package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;

public class DisplayBalance implements Screen {
	private Scanner scan = new Scanner(System.in);
	private User currentUser;
	
	

	public DisplayBalance(User currentUser) {
		super();
		this.currentUser = currentUser;
	}



	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		System.out.println("Current Balance");
		System.out.println("--------------------------");
		System.out.println(currentUser.getBalance() + "\n");
		System.out.println("Enter 0 to exit.");
		String selection = scan.nextLine();
		if(selection.equals("0")) {
			return new HomeScreen(currentUser);
		}
		System.out.println("Please press 0 to exit.");
		return this;
	}

}
