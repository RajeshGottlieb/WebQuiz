package com.webquiz.business;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    enum Type {
        UNKNOWN, FILL_IN_THE_BLANK, MULTIPLE_CHOICE, TRUE_FALSE
    }

    private int id = -1;
    private Type type = Type.UNKNOWN;
    private String text = "";
    private ArrayList<Answer> answers = new ArrayList<Answer>();

    public Question(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}