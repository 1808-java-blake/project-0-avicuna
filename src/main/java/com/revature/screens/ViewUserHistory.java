package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.Admin;
import com.revature.daos.AdminDao;

public class ViewUserHistory implements Screen {
	private Admin current;
	
	private Scanner scan = new Scanner(System.in);
	private AdminDao ad = AdminDao.currentAdminDao;
	

	public ViewUserHistory(Admin current) {
		super();
		// TODO Auto-generated constructor stub
		this.current = current;
	}

	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		System.out.println("Please enter the user's username to view their transaction history: ");
		String username = scan.nextLine();
		ad.viewUserTransactionHistory(username);
		return new AdminHomeScreen(current);
	}

}
