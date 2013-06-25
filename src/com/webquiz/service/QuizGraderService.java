package com.webquiz.service;

import com.webquiz.model.*;

public class QuizGraderService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Quiz quiz;
    

    public void setQuiz (Quiz newQuiz) {
    	quiz = newQuiz;
    }
    
    public Quiz getQuiz() {
    	return quiz;
    }
    
    public void grade() {
    	quiz.grade();
    }
}
