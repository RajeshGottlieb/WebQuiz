package com.webquiz.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webquiz.data.QuizSelectionDB;
import com.webquiz.data.UserDB;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;

public class LoginService extends WebQuizService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static final int MAX_QUESTION_COUNT = 10;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String url = "";
        String username = getParameter(request, "username");
        String password = getParameter(request, "password");
        User user = UserDB.getUser(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            QuizSelection selection = QuizSelectionDB.populate();
            request.setAttribute("selection", selection);
            url = "/selecttest.jsp";
        } else {
            if (!username.equals(""))
                request.setAttribute("error", "Sorry. Try again.");
            url = "/login.jsp";
        }
        forward(request, response, url);
    }
}
