<jsp:include page="include/header.jsp" />

<%@ page import="com.webquiz.model.Quiz" %>
<%@ page import="com.webquiz.model.Question" %>
<%@ page import="com.webquiz.model.Answer" %>

<h1>Quiz</h1>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="GRADE_QUIZ">
<%
Quiz quiz = (Quiz) request.getSession().getAttribute("quiz"); 

if (quiz != null) {
    for (Question question : quiz.getQuestions()) {
        int question_id = question.getId();
        Question.Type question_type = question.getType();
        String question_text = question.getText();
%>
        <%=question_text%><br /> <br />
<%
        if (question_type == Question.Type.FILL_IN_THE_BLANK) {
%>
            <input type="text" name="<%=question_id%>" size=40 value="">
<%
        } else if (question_type == Question.Type.MULTIPLE_CHOICE) {
            for (Answer answer : question.getAnswers()) {
                String answer_value = answer.getValue();
%>
                <input type="checkbox" name="<%=question_id%>" value="<%=answer_value%>"> <%=answer_value%><br />
<%
            }
        } else if (question_type == Question.Type.TRUE_FALSE) {
%>
            <input type="radio" name="<%=question_id%>" value="true"> true<br />
            <input type="radio" name="<%=question_id%>" value="false"> false
<%
        }
%>
        <hr>
<%
    }
}
%>
<br /> <input type="submit" value="Submit">
</form>

<%@ include file="include/footer.jsp"%>