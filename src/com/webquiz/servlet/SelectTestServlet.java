package com.webquiz.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.business.QuizSelection;
import com.webquiz.business.User;
import com.webquiz.data.QuizSelectionDB;

public class SelectTestServlet extends HttpServlet {

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

        String url = "";

        if (user != null) {
            QuizSelection selection = new QuizSelection();
            QuizSelectionDB.populate(selection);
            req.setAttribute("selection", selection);
            url = "/selecttest.jsp";
        } else {
            url = "/login.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
