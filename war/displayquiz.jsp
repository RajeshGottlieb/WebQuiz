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
			${question.text}
			
			<hr>

		</c:forEach>

		<br /> <input type="submit" value="Submit">
	</form>

</body>
</html>