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
}
