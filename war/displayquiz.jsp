<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%= application.getInitParameter("htmlDocType") %>
<html>

<jsp:include page="include/head.jsp" />
<body id="displayquiz">
<jsp:include page="include/header.jsp" />

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<h1>Quiz</h1>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="GRADE_QUIZ">
    <c:forEach var="question" items="${quiz.questions}">
        ${question.text}<br /><br />
        <c:choose>
            <c:when test="${question.type eq 'FILL_IN_THE_BLANK'}">
                <input type="text" name="${question.id}" size=40 value="">
            </c:when>
            <c:when test="${question.type eq 'MULTIPLE_CHOICE'}">
                <c:forEach var="answer" items="${question.answers}">
                    <input type="checkbox" name="${question.id}" value="${answer.value}"> ${answer.value}<br />
                </c:forEach>
            </c:when>
            <c:when test="${question.type eq 'TRUE_FALSE'}">
                <input type="radio" name="${question.id}" value="true"> true<br />
                <input type="radio" name="${question.id}" value="false"> false
            </c:when>
        </c:choose>
        <hr>
    </c:forEach>
    <br />
    <input type="submit" value="Submit">
</form>
</div>              <!-- //// End Content ////////////////////////////////////////////////////////////////////////// -->

<%@ include file="include/footer.jsp" %>
</body>
</html>