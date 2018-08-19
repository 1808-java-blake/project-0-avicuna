package com.revature.screens;

import java.util.ArrayList;
import java.util.Scanner;


import com.revature.beans.User;
import com.revature.daos.AdminDao;

public class ViewAllUsers implements Screen {
	
	private Scanner scan = new Scanner(System.in);
	private AdminDao ad = AdminDao.currentAdminDao;
	private ArrayList<User> userList = new ArrayList<User>();

	@Override
	public Screen start() {
		System.out.println("Users");
		System.out.println("---------------------------------------------------\n");
		userList = ad.viewAllUsers();
		for(User h: userList) {
			System.out.println(h);
		}
		System.out.println("\nEnter 0 to exit.");
		String selection = scan.nextLine();
		if(selection.equals("0")) {
			return new AdminHomeScreen();
		}
		System.out.println("Please press 0 to exit.");
		return this;
	}

}
