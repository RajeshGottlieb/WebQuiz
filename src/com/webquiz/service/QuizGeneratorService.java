package com.webquiz.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webquiz.data.QuizDB;
import com.webquiz.data.QuizSelectionDB;
import com.webquiz.model.Quiz;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;

public class QuizGeneratorService extends WebQuizService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static final int MAX_QUESTION_COUNT = 10;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String url = "";
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            String[] moduleParams = request.getParameterValues("module");
            if (moduleParams != null) {
                int[] modules = new int[moduleParams.length];
                for (int i = 0; i < moduleParams.length; ++i)
                    modules[i] = Integer.parseInt(moduleParams[i]);

                Quiz quiz = QuizDB.generate(modules, MAX_QUESTION_COUNT);
                httpSession.setAttribute("quiz", quiz);
                url = "/displayquiz.jsp";
            } else {
                request.setAttribute("error", "Please select one or more modules.");
                QuizSelection selection = QuizSelectionDB.populate();
                request.setAttribute("selection", selection);
                url = "/selecttest.jsp";
            }
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }
}
