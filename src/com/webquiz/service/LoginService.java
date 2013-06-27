package com.webquiz.service;

import com.webquiz.dao.UserDao;
import com.webquiz.model.User;

public class LoginService {

    public static boolean addUser(String username, String password) {
        return UserDao.addUser(username, password);
    }

    public static User getUser(String username, String password) {
        return UserDao.getUser(username, password);
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
        if (!password.matches(".*[a-z].*"))
            return false;
        if (!password.matches(".*[A-Z].*"))
            return false;

        return true;
    }
}
