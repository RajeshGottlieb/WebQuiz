package com.webquiz.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webquiz.data.QuizSelectionDB;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;

public class QuizSelectorService extends WebQuizService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String url = "";
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            QuizSelection selection = QuizSelectionDB.populate();
            request.setAttribute("selection", selection);
            url = "/selecttest.jsp";
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }
}
