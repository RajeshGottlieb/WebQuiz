package com.webquiz.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.business.User;
import com.webquiz.data.UserDB;

public class SelectTestServlet extends HttpServlet {

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
            // TODO need to implement a QuizSelection object for use in selecttest.jsp
            url = "/selecttest.jsp";
        } else {
            url = "/login.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
