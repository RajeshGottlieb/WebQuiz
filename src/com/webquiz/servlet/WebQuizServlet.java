package com.webquiz.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.*;

import com.webquiz.data.QuizDB;
import com.webquiz.data.QuizSelectionDB;
import com.webquiz.data.UserDB;
import com.webquiz.model.Question;
import com.webquiz.model.Quiz;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;

public class WebQuizServlet extends HttpServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	String getParameter(HttpServletRequest request, String name, String def) {
		String val = request.getParameter(name);
		return (val != null) ? val : def;
	}

	String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, "");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = getParameter(request, "action", "LOGIN");
		System.out.println("action is " + action);

		if ((request.getSession().getAttribute("user") == null)
				&& !action.equals("LOGIN") && !action.equals("REGISTER")
				&& !action.equals("NEWUSER"))
			action = "LOGIN";
		System.out.println("action is " + action);

		if (action.equals("LOGIN"))
			login(request, response);
		else if (action.equals("REGISTER"))
			register(request, response);
		else if (action.equals("NEWUSER"))
			newUser(request, response);
		else if (action.equals("LOGOUT"))
			logout(request, response);
		else if (action.equals("SELECT_QUIZ"))
			selectQuiz(request, response);
		else if (action.equals("GENERATE_QUIZ"))
			generateQuiz(request, response);
		else if (action.equals("GRADE_QUIZ"))
			gradeQuiz(request, response);
	}

	void forward(HttpServletRequest request, HttpServletResponse response,
			String url) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "";
		String username = getParameter(request, "username");
		String password = getParameter(request, "password");

		if (username.equals("") || password.equals("")) {
			request.setAttribute("error",
					"Sorry, invalid username or password. Try again.");
			url = "/login.jsp";
		} else {
			User user = UserDB.getUser(username, password);

			if (user != null) {
				request.getSession().setAttribute("user", user);
				selectQuiz(request, response);
				return;
			} else {
				if (!username.equals(""))
					request.setAttribute("error",
							"Sorry, wrong username/password. Try again.");
				url = "/login.jsp";
			}
		}
		forward(request, response, url);
	}

	void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/register.jsp";
		forward(request, response, url);
	}

	void newUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "";
		String username = getParameter(request, "username");
		String password = getParameter(request, "password");

		if (username.equals("") || password.equals("")) {
			request.setAttribute("error",
					"Sorry, invalid username or password. Try again.");
			url = "/register.jsp";
		} else {

			boolean rc = UserDB.addUser(username, password);

			if (rc == true) {
				url = "/login.jsp";
			} else {
				request.setAttribute("error", "Sorry, bad username. Try again.");
				url = "/register.jsp";
			}
		}
		forward(request, response, url);
	}

	void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		String url = "/login.jsp";
		forward(request, response, url);
	}

	void selectQuiz(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "";
		User user = (User) request.getSession().getAttribute("user");

		if (user != null) {
			QuizSelection selection = QuizSelectionDB.populate();
			request.setAttribute("selection", selection);
			url = "/selecttest.jsp";
		} else {
			url = "/login.jsp";
		}
		forward(request, response, url);
	}

	void generateQuiz(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "";
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("user");

		if (user != null) {
			String[] moduleParams = request.getParameterValues("module");
			if (moduleParams != null) {
				int[] modules = new int[moduleParams.length];
				for (int i = 0; i < moduleParams.length; ++i)
					modules[i] = Integer.parseInt(moduleParams[i]);

				final int MAX_QUESTION_COUNT = 10;
				Quiz quiz = QuizDB.generate(modules, MAX_QUESTION_COUNT);

				httpSession.setAttribute("quiz", quiz);
				url = "/displayquiz.jsp";
			} else {
				request.setAttribute("error",
						"Please select one or more modules.");
				QuizSelection selection = QuizSelectionDB.populate();
				request.setAttribute("selection", selection);
				url = "/selecttest.jsp";
			}
		} else {
			url = "/login.jsp";
		}
		forward(request, response, url);
	}

	void gradeQuiz(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		forward(request, response, url);
	}
}
