package com.revature.screens;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class DepositScreen implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private AppState state = AppState.state;
	private User currentUser = state.getCurrentUser();
	private UserDao ud = UserDao.currentUserDao;
	private Logger log = Logger.getRootLogger();



	@Override
	public Screen start() {

		log.debug("started deposit screen");
		System.out.println("Which account would you like to deposit into?: ");
		String account = scan.nextLine().toLowerCase();
		System.out.println("How much YeetCoin would you like to deposit?: ");
		double amount = scan.nextDouble();	
		String username = currentUser.getUsername();
		int userId = currentUser.getUserId();
		ud.depositYeet(amount, username, account);
		ud.addDepositHistory(amount, userId);
		return new HomeScreen();
	}

}
