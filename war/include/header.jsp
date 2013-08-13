<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" class="bgOne">  <!-- //// Start Header/Navigation //////////////////////////////////////////////// -->
    <ul id="nav" >
        <li id="logo" class="fl"><h1>${initParam.siteName}</h1></li>
        <li class="fl hoverA"><a href="/WebQuiz/Servlet?action=ABOUT">About</a></li>
        <li class="fl hoverA"><a href="/WebQuiz/Servlet?action=SELECT_QUIZ">Quizzes</a></li>
<c:choose>
    <c:when test="${!(empty user.username)}">
        <li class="fr hoverA"><a href="/WebQuiz/Servlet?action=LOGOUT">Logout</a></li>
        <li class="fr pr10">Welcome ${user.username}</li>
    </c:when>
    <c:otherwise>
        <li class="fr hoverA"><a href="/WebQuiz/">Login</a></li>
    </c:otherwise>
</c:choose>
    </ul>
</div>                        <!-- //// End Header/Navigation ///////////////////////////////////////////////////// -->
