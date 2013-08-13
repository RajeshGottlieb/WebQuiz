<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>${initParam.htmlDocType}
<html>

<jsp:include page="include/head.jsp" />
<body id="login">
<jsp:include page="include/header.jsp" />

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<div class="pct50 fr">
    <h1>Welcome to ${initParam.siteName}</h1>
    <p>To access a quiz, you will need to:
    </p>
    <p><a class="link pl20" href="/WebQuiz/Servlet?action=NEWUSER">Register</a>
    </p>
    <p>a new account and then <strong>Login</strong>.
        The site offers a number of quizzes that will allow you to evaluate your knowledge
        or practice to improve upon a skill.
    </p>
    <p>So, what are you waiting for? Login and get started.
    </p>
</div>

<div class="pct50 fl">
<h1>Login to ${initParam.siteName}</h1>

<c:if test="${!(empty error)}">
    <p class="error">${error}</p>
</c:if>

<form method="post" action="Servlet">
    <p>Please enter your User Name and Password.</p>
    <table cellpadding='4' cellspacing='2'>
        <tr>
            <td>User Name:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"></td>
        </tr>
    </table>
    <p>
        <input type="submit" name="action" value="LOGIN">
    </p>
</form>
<p>
Please <a href="/WebQuiz/Servlet?action=NEWUSER">Register as a New User</a> if you do not have an account. 
</p>
</div>
<div class="cb"></div>
</div>              <!-- //// End Content ////////////////////////////////////////////////////////////////////////// -->

<%@ include file="include/footer.jsp" %>
</body>
</html>