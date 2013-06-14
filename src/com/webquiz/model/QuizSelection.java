package com.webquiz.model;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizSelection implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Subject> subjects = new ArrayList<Subject>();
    
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
