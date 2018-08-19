package com.revature.screens;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.Admin;
import com.revature.daos.AdminDao;
import com.revature.util.AppState;

public class AdminLoginScreen implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private AdminDao ad = AdminDao.currentAdminDao;
	private Logger log = Logger.getRootLogger();
	private AppState state = AppState.state;


	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		System.out.println("Enter Username: ");
		String username = scan.nextLine();
		log.trace("username = " + username);
		
		System.out.println("Enter Password: ");
		String password = scan.nextLine();
		
		log.debug("received admins credentials");
		Admin currentAdmin = ad.findByUsernameAndPassword(username, password);
		if (currentAdmin != null) {
			state.setCurrentAdmin(currentAdmin);
			log.info("admin succefully logged in");
			log.info("Welcome, " + currentAdmin);
			return new AdminHomeScreen();
		}

		System.out.println("unable to login");
		return this;
	}

}
