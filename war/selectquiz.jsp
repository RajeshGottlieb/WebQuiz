<jsp:include page="include/header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="pct50 fr">
    <h1>Welcome to Select <%= application.getInitParameter("siteName") %></h1>
    <h2>Organization</h2>
    <h3 class="pl20">Quizzes are organized by:</h3>
    <ul class="pl20">
        <li>Subject</li>
        <li>Category</li>
        <li>Module</li>
    </ul>
    <h2>Questions</h2>
    <p>Quiz questions and answer choices are randomized each time you take a quiz.
        So, you can take a quiz again and again to help you learn and achieve a perfect score.
    </p>
    <h3 class="pl20">Questions may be:</h3>
    <ul class="pl20">
        <li>Multiple Choice</li>
        <li>True | False</li>
        <li>Fill in the blank</li>
    </ul>
    <p>If you would like to quiz yourself over more than one skill-set, check more than one box.
        You can check several boxes to create a randomized quiz that covers several modules.
    <h2>Assessment</h2>
    <p>When you click the Submit button at the bottom of the quiz, your answers will be checked.
        You'll find out which questions you got correct and which ones not.
        Until then, you can double check your answers and change any of your responses.
    </p>
</div>

<div class="pct50 fl">
    <h1>Select module(s) to be quizzed on</h1>
    <c:if test="${error != ''}">
        <p class="error">${error}</p>
    </c:if>

    <form method="post" action="Servlet">
        <input type="hidden" name="action" value="GENERATE_QUIZ">
        <ul>
            <c:forEach var="subject" items="${selection.subjects}">
                <li><h2>${subject.name}</h2></li>
                <ul>
                    <c:forEach var="category"
                        items="${subject.categories}">
                        <li><h3 class="pl30">${category.name}</h3></li>
                        <ul class="pb20">
                            <c:forEach var="module"
                                items="${category.modules}">
                                <li class="pl60"><input type="checkbox" name="module" value="${module.id}">${module.name}</li>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </ul>
            </c:forEach>
        </ul>
        <br />
        <input type="submit" value="Submit">
    </form>
</div>
<div class="cb"></div>

<%@ include file="include/footer.jsp"%>