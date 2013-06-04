package com.webquiz.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.business.User;
import com.webquiz.data.UserDB;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));

        if (UserDB.validate(user)) {
            req.setAttribute("user", user);
            String url = "/selecttest.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("error", "Sorry. Try again.");
            String url = "/login.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(req, resp);
        }
    }
}
