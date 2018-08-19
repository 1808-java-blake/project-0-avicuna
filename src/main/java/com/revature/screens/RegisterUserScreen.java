package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;

public class RegisterUserScreen implements Screen {

	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		User u = new User();
		System.out.println("Enter new username");
		String username = scan.nextLine();
		boolean check = ud.findUsername(username);
		if(check) {
			System.out.println("That username is currently in use. Please try another one.");
			return this;
		}
		u.setUsername(username);
		System.out.println("Enter password");
		u.setPassword(scan.nextLine());
		System.out.println("Enter first name");
		u.setFirstName(scan.nextLine());
		System.out.println("Enter last name");
		u.setLastName(scan.nextLine());
		System.out.println("Enter age");
		String age = scan.nextLine();
		
		try {
			u.setAge(Integer.valueOf(age));
			u.setBalance(0);
			ud.createUser(u);
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid number");
		}
		
		return new UserLoginScreen();
	}

}
