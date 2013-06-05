package com.webquiz.business;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name = "";
    private int id = -1;
    private ArrayList<Category> categories = new ArrayList<Category>();

    public Subject(String name, int id) {
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

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
