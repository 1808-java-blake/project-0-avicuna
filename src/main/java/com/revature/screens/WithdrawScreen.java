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
	boolean b = true;

	@Override
	public Screen start() {
		log.debug("withdraw screen started");
		while(b) {
			System.out.println("How much YeetCoin would you like to withdraw?: ");
			String username = currentUser.getUsername();
			int userId = currentUser.getUserId();
			double thisNum = scan.nextDouble();
			double balance = ud.getBalance(username);
			if(thisNum > balance) {
				System.out.println("Unable to withdraw this amount!");
			}
			else {
				ud.withdrawYeet(balance, thisNum, username);
				ud.addWithdrawalHistory(thisNum, userId);
				b = false;
			}
		}
		return new HomeScreen();
	}

}
