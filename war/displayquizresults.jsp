<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%= application.getInitParameter("htmlDocType") %>
<html>

<jsp:include page="include/head.jsp" />
<body id="displayquizresults">
<jsp:include page="include/header.jsp" />

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<h1>Quiz Results</h1>

<a href="/WebQuiz/Servlet?action=SELECT_QUIZ">Try another quiz</a><br /><br />

<em>${quiz.correctCount} out of ${quiz.questionCount} correct</em>
<hr>

<c:forEach var="question" items="${quiz.questions}">
    ${question.text}<br /> <br />
    Your answer:<br />
    <c:forEach var="userAnswer" items="${question.userAnswers}">
        ${userAnswer}<br />
    </c:forEach>
    <br />
    <c:choose>
        <c:when test="${question.answeredCorrectly}">
            <font color="green">Correct</font>
        </c:when>
        <c:otherwise>
            <font color="red">Wrong</font>
        </c:otherwise>
    </c:choose>
    <hr>
</c:forEach>

<a href="/WebQuiz/Servlet?action=SELECT_QUIZ">Try another quiz</a>
</div>              <!-- //// End Content ////////////////////////////////////////////////////////////////////////// -->

<%@ include file="include/footer.jsp" %>
</body>
</html>