package com.webquiz.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.service.Service;
import com.webquiz.service.LoginService;
import com.webquiz.service.QuizGraderService;
import com.webquiz.service.QuizGeneratorService;
import com.webquiz.service.QuizSelectorService;

public class WebQuizServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    HashMap<String, Service> serviceMap = new HashMap<String, Service>();

    @Override
    public void init() throws ServletException {
        super.init();
        serviceMap.put("LOGIN", new LoginService());
        serviceMap.put("SELECT_QUIZ", new QuizSelectorService());
        serviceMap.put("GENERATE_QUIZ", new QuizGeneratorService());
        serviceMap.put("GRADE_QUIZ", new QuizGraderService());
    }

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

        if (request.getSession().getAttribute("user") == null)
            action = "LOGIN";

        Service service = serviceMap.get(action);
        service.handleRequest(request, response);
    }
}
