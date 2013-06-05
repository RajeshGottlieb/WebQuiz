package com.webquiz.business;

import java.io.Serializable;

public class Answer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int id = -1;
    private boolean correct = false;
    private String value = "";

    public Answer(int id, boolean correct, String value) {
        this.id = id;
        this.correct = correct;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = (value != null) ? value : "";
    }
}