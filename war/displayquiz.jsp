<!DOCTYPE html>
<html>
<head>
<title>Select Test | WebQuiz</title>
</head>
<body>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <h1>Quiz</h1>

    <!-- This needs more work -->

    <form method="post" action="CheckQuiz">
        <c:forEach var="question" items="${quiz.questions}">
            ${question.text}<br />
            <c:choose>
                <c:when test="${question.type eq 'FILL_IN_THE_BLANK'}">
				fill in the blank
				</c:when>
                <c:when test="${question.type eq 'MULTIPLE_CHOICE'}">
                multiple choice
                </c:when>
                <c:when test="${question.type eq 'TRUE_FALSE'}">
                    <input type="radio" name="${question.id}" value="true"> true<br />
                    <input type="radio" name="${question.id}" value="false"> false
                </c:when>
            </c:choose>
            <hr>

        </c:forEach>

        <br /> <input type="submit" value="Submit">
    </form>

</body>
</html>