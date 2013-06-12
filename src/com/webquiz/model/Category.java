package com.webquiz.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name = "";
    private int id = -1;
    private ArrayList<Module> modules = new ArrayList<Module>();

    public Category(String name, int id) {
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
    
    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
}
