package com.webquiz.business;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Question> questions = new ArrayList<Question>();
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
