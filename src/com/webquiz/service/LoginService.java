package com.webquiz.service;


import com.webquiz.data.UserDB;
import com.webquiz.model.User;

public class LoginService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
	public static boolean addUser(String username, String password) {
		return UserDB.addUser(username, password);
	}

	public static User getUser(String username, String password) {
		return UserDB.getUser(username, password);
	}

	public static boolean validateUsername(String username) {
		if (username.equals(""))
			return false;
		else
			return true;
	}
	
	public static boolean validatePassword(String password) {
		if (password.equals(""))
			return false;
		if (password.length() < 6)
			return false;
		if (!password.matches(".*[0-9].*"))
			return false;
		return true;
	}
}
