package com.webquiz.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webquiz.model.Question;
import com.webquiz.model.Quiz;
import com.webquiz.model.User;

public class QuizGraderService extends WebQuizService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String url = "";
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            Quiz quiz = (Quiz) httpSession.getAttribute("quiz");

            if (quiz != null) {
                for (Question question : quiz.getQuestions()) {

                    String question_id = "" + question.getId();

                    // get the users answer(s)
                    ArrayList<String> userAnswers = new ArrayList<String>();
                    String[] answers = request.getParameterValues(question_id);
                    if (answers != null)
                        userAnswers.addAll(Arrays.asList(answers));
                    question.setUserAnswers(userAnswers);
                }
                quiz.grade();
                url = "/displayquizresults.jsp";
            } else {
                url = "/selecttest.jsp";
            }
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }
}
