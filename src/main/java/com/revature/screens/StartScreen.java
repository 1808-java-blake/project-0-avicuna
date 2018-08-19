package com.revature.screens;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class StartScreen implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private Logger log = Logger.getRootLogger();

	@Override
	public Screen start() {
		log.debug("started login screen");
		System.out.println("Enter 1 for User login page");
		System.out.println("Enter 2 for User registration page");
		System.out.println("Enter 3 for Admin login page");
		String choice = scan.nextLine();
		switch(choice) {
		case "1":
			return new UserLoginScreen();
		case "2":
			return new RegisterUserScreen();
		case "3":
			return new AdminLoginScreen();
					
		}
		return this;
	}
}
