package com.webquiz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webquiz.model.Question;
import com.webquiz.model.Quiz;
import com.webquiz.model.QuizSelection;
import com.webquiz.model.User;
import com.webquiz.service.*;
import com.webquiz.utility.FormValidator;

public class WebQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private String getParameter(HttpServletRequest request, String name, String def) {
        String val = request.getParameter(name);
        return (val != null) ? val : def;
    }

    private String getParameter(HttpServletRequest request, String name) {
        return getParameter(request, name, "");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getParameter(request, "action", "LOGIN");
        System.out.println("action is " + action);

        if ((request.getSession().getAttribute("user") == null)
                && !action.equals("LOGIN") && !action.equals("REGISTER")
                && !action.equals("ABOUT") && !action.equals("NEWUSER")) {
            action = "LOGIN";
        }
        System.out.println("action is " + action);

        if      ("LOGIN".equals(action))        {login(request, response);}
        else if ("NEWUSER".equals(action))      {newUser(request, response);}
        else if ("REGISTER".equals(action))     {register(request, response);}
        else if ("LOGOUT".equals(action))       {logout(request, response);}
        else if ("SELECT_QUIZ".equals(action))  {selectQuiz(request, response);}
        else if ("GENERATE_QUIZ".equals(action)){generateQuiz(request, response);}
        else if ("GRADE_QUIZ".equals(action))   {gradeQuiz(request, response);}
        else if ("ABOUT".equals(action))        {about(request, response);}
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        String username = getParameter(request, "username");
        String password = getParameter(request, "password");

        if (!FormValidator.validateEmptyField(username) || !FormValidator.validateEmptyField(password)) {
            request.setAttribute("error", "Please supply a User Name and Password.");
            url = "/login.jsp";
        } else {
            User user = LoginService.getUser(username, password);

            if (user != null) {
                request.getSession().setAttribute("user", user);
                selectQuiz(request, response);
                return;
            } else {
                request.setAttribute("error",
                        "Sorry, wrong username/password. Try again.");
                url = "/login.jsp";
            }
        }
        forward(request, response, url);
    }
	
    private void newUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "/register.jsp");
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        String username = getParameter(request, "username");
        String password = getParameter(request, "password");

        if (!FormValidator.validateEmptyField(username) || !FormValidator.validateEmptyField(password)) {
            request.setAttribute("error", "Please supply a User Name and Password.");
            url = "/register.jsp";
        } else if (!FormValidator.validatePassword(password)) {
            request.setAttribute("error",
                    "Invalid password. Password must be 8 or more characters.<br>" +
                    "Must contain a number, and a lower and uppercase letter.");
            url = "/register.jsp";
        } else {
            boolean rc = LoginService.addUser(username, password);

            if (rc == true) {
                url = "/login.jsp";
            } else {
                request.setAttribute("error", "Sorry, bad username. Try again.");
                url = "/register.jsp";
            }
        }
        forward(request, response, url);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        String url = "/login.jsp";
        forward(request, response, url);
    }

    private void selectQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        QuizSelection selection = null;
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            QuizSelectorService qss = new QuizSelectorService();
            if (qss != null)        {selection = qss.populate();}
            if (selection != null)  {request.setAttribute("selection", selection);}
            url = "/selectquiz.jsp";
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }

    private void generateQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            String[] moduleParams = request.getParameterValues("module");
            if (moduleParams != null) {
                int[] modules = new int[moduleParams.length];
                for (int i = 0; i < moduleParams.length; ++i) {
                    modules[i] = Integer.parseInt(moduleParams[i]);
                }
                final int MAX_QUESTION_COUNT = 10;
                Quiz quiz = QuizGeneratorService.generate(modules, MAX_QUESTION_COUNT);

                httpSession.setAttribute("quiz", quiz);
                url = "/displayquiz.jsp";
            } else {
                request.setAttribute("error", "Please select one or more modules.");
                QuizSelectorService qss = new QuizSelectorService();
                QuizSelection selection = qss.populate();
                request.setAttribute("selection", selection);
                url = "/selectquiz.jsp";
            }
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }

    private void gradeQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            QuizGraderService qgs = new QuizGraderService();
            Quiz quiz = null;

            if (qgs != null) {
                // get questions which are inside quiz
                quiz = (Quiz) httpSession.getAttribute("quiz");
                if (quiz != null) {
                    qgs.setQuiz(quiz);

                    // get the users answer(s)
                    for (Question question : quiz.getQuestions()) {
                        String question_id = "" + question.getId();
                        ArrayList<String> userAnswers = new ArrayList<String>();
                        String[] answers = request.getParameterValues(question_id);
                        if (answers != null) {
                            userAnswers.addAll(Arrays.asList(answers));
                        }
                        question.setUserAnswers(userAnswers);
                    }
                }
                qgs.grade();
                httpSession.setAttribute("quiz", qgs.getQuiz());
            }
            url = "/displayquizresults.jsp";
        } else {
            url = "/login.jsp";
        }
        forward(request, response, url);
    }

    private void about(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = "" +
                        "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<meta charset=\"UTF-8\">" +
                        "<title>Web Quiz</title>" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reset.css\" />" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/site.css\" />" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/page.css\" />" +
                        "</head>" +
                        "<body>" +
                        "<div id=\"header\" class=\"bgOne\">  <!-- //// Start Navigation ////////////////////////////////////////////////////////// -->" +
                        "    <ul id=\"nav\" >" +
                        "        <li id=\"logo\" class=\"fl\"><h1>Web Quiz</h1></li>" +
                        "        <li class=\"fl hoverA\"><a href=\"/WebQuiz/Servlet?action=ABOUT\">About</a></li>" +
                        "        <li class=\"fr hoverA\"><a href=\"/WebQuiz/\">Login</a></li>" +
                        "    </ul>" +
                        "</div>                        <!-- //// End Navigation //////////////////////////////////////////////////////////// -->" +
                        "<div id=\"content\">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->";
        String footer = "" +
                        "</div>              <!-- //// End Content ///////////////////////////////////////////////////////////////////////// -->" +
                        "<div id=\"footer\">   <!-- //// Start Footer //////////////////////////////////////////////////////////////////////// -->" +
                        "    <div class=\"fr\">" +
                        "&copy; 2013 Web Quiz Group" +
                        "    </div>" +
                        "</div>              <!-- //// End Footer ////////////////////////////////////////////////////////////////////////// -->" +
                        "</body>" +
                        "</html>";
        String content ="" +
                        "<h1>About</h1>" +
                        "<h2>Web Quiz Group Members</h2>" +
                        "<ul>" +
                        "    <li>Sifang Chu</li>" +
                        "    <li>Rajesh Gottlieb</li>" +
                        "    <li>Keith Sproull</li>" +
                        "</ul>" +
                        "<h2>About Us</h2>" +
                        "<p>" +
                        "Lorem ipsum dolor sit amet, bonorum recusabo cu usu. Tritani tibique definitiones at mea, inani postea detracto pri" + 
                        "ea. Et commune sententiae nam. Usu ad eros tollit, augue liber ex per. Salutandi corrumpit disputationi ea qui. Vis" + 
                        "debet neglegentur an, purto sonet iracundia vim ut." +
                        "</p>" +
                        "<p>" +
                        "Dicta molestiae similique mel at. Iudicabit repudiare prodesset nam ut. Ut summo assueverit contentiones vix, nec eu" + 
                        "fabellas insolens. Nostrum scaevola deserunt nec ne. Duo te aeterno epicurei interesset. Ut fugit volumus his, per" + 
                        "latine lucilius eu." +
                        "</p>" +
                        "<p>" +
                        "Cu assum melius intellegat est, maiorum facilisi patrioque ut mel. Te sea quot aliquando, cum assum postea ne, quem" + 
                        "graece eam no. His posse ignota pericula ne, aeterno habemus vituperatoribus ne per, te autem zril his. No usu" + 
                        "labore nusquam placerat, modus omnes scribentur pro eu. Tale iusto cu usu. Ea oporteat sententiae mei, te oportere" + 
                        "salutatus pro, est hinc accumsan cu." +
                        "</p>" +
                        "<p>" +
                        "Sea enim rebum utroque no. Et sit legere suscipiantur deterruisset, impetus scribentur no nec. Alterum vulputate" + 
                        "eum ei. Labores repudiandae eos eu, eum eu causae voluptatibus. Ex volumus insolens est." +
                        "</p>";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(header);
        out.println(content);
        out.println(footer);
        out.close();
    }
}
