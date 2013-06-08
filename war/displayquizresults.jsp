<!DOCTYPE html>
<html>
<head>
<title>Quiz Results | WebQuiz</title>
</head>
<body>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <h1>Quiz Results</h1>

    <!-- This needs more work -->

    <em>${quiz.correctCount} out of ${quiz.questionCount} correct</em>
    <hr>

    <c:forEach var="question" items="${quiz.questions}">
        ${question.text}<br />
        <br />

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

</body>
</html>