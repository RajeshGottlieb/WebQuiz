package com.webquiz.model;

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

    public void grade() {
        for (Question question : getQuestions())
            question.grade();
    }
    
    public int getQuestionCount() {
        return questions.size();
    }
    
    public int getCorrectCount() {
        int correctCount = 0;
        for (Question question : getQuestions())
            if (question.isAnsweredCorrectly())
                ++correctCount;
        return correctCount;
    }
}
