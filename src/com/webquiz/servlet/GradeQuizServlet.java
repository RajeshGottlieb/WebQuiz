package com.webquiz.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.business.Quiz;
import com.webquiz.business.User;

public class GradeQuizServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        String url = "/login.jsp";

        if (user != null) {
            Quiz quiz = (Quiz) httpSession.getAttribute("quiz");

            if (quiz != null) {
                url = "/displayquizresults.jsp";    
            }
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
