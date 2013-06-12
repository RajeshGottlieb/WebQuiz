package com.webquiz.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.model.Question;
import com.webquiz.model.Quiz;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;
import com.webquiz.data.QuizDB;
import com.webquiz.data.QuizSelectionDB;
import com.webquiz.data.UserDB;

public class WebQuizServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static final int MAX_QUESTION_COUNT = 10;

    String getParameter(HttpServletRequest request, String name, String def) {
        String val = request.getParameter(name);
        return (val != null) ? val : def;
    }

    String getParameter(HttpServletRequest request, String name) {
        return getParameter(request, name, "");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String action = getParameter(request, "action", "LOGIN");
        String url = "";

        if (action.equals("LOGIN")) {
            url = login(request, response);
        } else if (action.equals("SELECT_QUIZ")) {
            url = selectQuiz(request, response);
        } else if (action.equals("GRADE_QUIZ")) {
            url = gradeQuiz(request, response);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        return url;
    }

    String selectQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            String[] moduleParams = request.getParameterValues("module");
            int[] modules = new int[moduleParams.length];
            for (int i = 0; i < moduleParams.length; ++i)
                modules[i] = Integer.parseInt(moduleParams[i]);

            Quiz quiz = QuizDB.generate(modules, MAX_QUESTION_COUNT);
            httpSession.setAttribute("quiz", quiz);
            url = "/displayquiz.jsp";
        } else {
            url = "/login.jsp";
        }
        return url;
    }

    String gradeQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        return url;
    }
}
