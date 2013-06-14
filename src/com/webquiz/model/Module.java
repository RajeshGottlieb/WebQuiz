package com.webquiz.model;

import java.io.Serializable;

public class Module implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name = "";
    private int id = -1;

    public Module(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
