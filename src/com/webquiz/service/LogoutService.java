package com.webquiz.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webquiz.data.QuizSelectionDB;
import com.webquiz.data.UserDB;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;

public class LogoutService extends WebQuizService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
		String url = "";

		request.getSession().removeAttribute("user");
		url = "/login.jsp";
       
        forward(request, response, url);
    }
}
