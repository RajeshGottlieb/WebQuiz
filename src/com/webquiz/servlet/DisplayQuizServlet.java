package com.webquiz.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.business.Quiz;
import com.webquiz.business.User;
import com.webquiz.data.QuizDB;

public class DisplayQuizServlet extends HttpServlet {

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
        int maxQuestionCount = 10;
        String url = "";

        if (user != null) {
            String[] moduleParams = req.getParameterValues("module");            
            int[] modules = new int[moduleParams.length];
            for (int i = 0; i < moduleParams.length; ++i)
                modules[i] = Integer.parseInt(moduleParams[i]);
            
            Quiz quiz = new Quiz();
            QuizDB.generate(quiz, modules, maxQuestionCount);
            req.setAttribute("quiz", quiz);
            url = "/displayquiz.jsp";
        } else {
            url = "/login.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
