package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserSerializer;

public class DisplayHistory implements Screen {
	private Scanner scan = new Scanner(System.in);
	private User currentUser;
	
	public DisplayHistory(User currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public Screen start() {
		System.out.println("Transaction History");
		System.out.println("----------------------------------");
		System.out.println(currentUser.getHistory() + "\n");
		System.out.println("Enter 0 to exit.");
		String selection = scan.nextLine();
		if(selection.equals("0")) {
			return new HomeScreen(currentUser);
		}
		System.out.println("Please press 0 to exit.");
		return this;
	}

}
