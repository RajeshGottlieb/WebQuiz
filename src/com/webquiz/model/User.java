package com.webquiz.model;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username = "";
    private String password = "";
    private int id = -1;

    public User(String username, String password, int id) {
    	setUsername(username);
    	setPassword(password);
    	setId(id);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = (username != null) ? username : "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = (password != null) ? password : "";
    }

    private int getId() {
        return id;
    }
    
    private void setId(int id) {
        this.id = id;
    }
}
