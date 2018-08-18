package com.revature.screens;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

public class UserLoginScreen implements Screen {

	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private Logger log = Logger.getRootLogger();
	private AppState state = AppState.state;

	@Override
	public Screen start() {
		System.out.println("Enter Username: ");
		String username = scan.nextLine();
		log.trace("username = " + username);
		
		System.out.println("Enter Password: ");
		String password = scan.nextLine();
		
		log.debug("received users credentials");
		User currentUser = ud.findByUsernameAndPassword(username, password);
		if (currentUser != null) {
			state.setCurrentUser(currentUser);
			log.info("user succefully logged in");
			log.info("welcome" + currentUser);
			return new HomeScreen();
		}

		System.out.println("unable to login");
		return this;
	}

}
