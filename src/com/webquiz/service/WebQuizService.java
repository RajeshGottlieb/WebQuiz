package com.webquiz.service;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebQuizService implements Service, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    String getParameter(HttpServletRequest request, String name, String def) {
        String val = request.getParameter(name);
        return (val != null) ? val : def;
    }

    String getParameter(HttpServletRequest request, String name) {
        return getParameter(request, name, "");
    }
    
    void forward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException,
            IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
