package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class WireTransfer implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private AppState state = AppState.state;
	private User currentUser = state.getCurrentUser();
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		String username = currentUser.getUsername();
		System.out.println("What is the username of the person you want to transfer money to?: ");
		String recipientUsername = scan.nextLine();
		boolean check = ud.findUsername(recipientUsername);
		if(!check) {
			System.out.println("This username does not exist, please try again.");
			return this;
		}
		System.out.println("Which account do you want to transfer from?: ");
		String account = scan.nextLine().toLowerCase();
		System.out.println("What is the amount that you would like to transfer?: ");
		double amount = scan.nextDouble();
		ud.wireTransfer(amount, username, account, recipientUsername);
		ud.addWireTransferHistory(amount, username);
		System.out.println("Transfer successful!");
		return new HomeScreen();
	}

}
