package com.webquiz;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
	
	public class StudentObj
	{
		String _userName;
		StudentObj (String userNameInput) 
		{
			_userName = userNameInput;
		};
	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userNameString=req.getParameter("username");
		String passwordString=req.getParameter("password");
		

		if (validLogin(userNameString, passwordString)) {
			StudentObj studentObj = new StudentObj(userNameString);
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("studentObj", (Object)studentObj);
			showLoginSuccessForm(resp, userNameString);
		}
		else
		{
			showLoginDenyForm(resp);
		}


	}

	private boolean validLogin(String userNameString, String passwordString)
	{
		if (userNameString.endsWith("1"))
		    return true;
		else 
			return false;
	}
	
	private void showLoginSuccessForm(HttpServletResponse resp, String userNameString) throws IOException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		out.println("Welcome " + userNameString + " to Web Quiz!");
		out.println("</body></html>");
		out.close();
	}
	
	private void showLoginDenyForm(HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		out.println("Sorry. Try again. ");
		out.println("<a href=login.html>Click Me</a>");
		out.println("</body></html>");
		out.close();
	}
}
