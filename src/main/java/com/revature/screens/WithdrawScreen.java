package com.revature.screens;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class WithdrawScreen implements Screen {
	
	private AppState state = AppState.state;
	private User currentUser = state.getCurrentUser();
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private Logger log = Logger.getRootLogger();

	@Override
	public Screen start() {
		log.debug("withdraw screen started");
		
		double balance = -1;
		System.out.println("Which account would you like to withdraw from?: ");
		String account = scan.nextLine();
		account = account.toLowerCase();
		
		System.out.println("How much YeetCoin would you like to withdraw?: ");
		double thisNum = scan.nextDouble();
		
		String username = currentUser.getUsername();
		int userId = currentUser.getUserId();
		
		if(account.equals("checking"))
			balance = ud.getCheckingBalance(username);
		else if(account.equals("savings"))
			balance = ud.getSavingsBalance(username);
		
		if(thisNum > balance) {
			System.out.println("Sorry looks like you have insufficient funds.");
			return new HomeScreen();
		}
		else if (thisNum < 0) {
			System.out.println("Amount can't be a negative number!");
			return new HomeScreen();
		}
		else if (thisNum == 0) {
			System.out.println("Amount can't be zero!");
			return new HomeScreen();
		}
		else {
			ud.withdrawYeet(thisNum, username, account);
			ud.addWithdrawalHistory(thisNum, userId);
		}

		return new HomeScreen();
	}

}
