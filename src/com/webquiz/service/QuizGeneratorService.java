package com.webquiz.service;

import com.webquiz.dao.QuizGeneratorDao;
import com.webquiz.model.Question;
import com.webquiz.model.Quiz;

public class QuizGeneratorService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static final int MAX_QUESTION_COUNT = 10;

    public static Quiz generate(int[] modules, int maxQuestionCount) {
        Quiz quiz = new Quiz();
        try {
            quiz.setQuestions(QuizGeneratorDao.getQuestions(modules, maxQuestionCount));

            for (Question question : quiz.getQuestions())
                question.setAnswers(QuizGeneratorDao.getAnswers(question));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return quiz;
    }
}
