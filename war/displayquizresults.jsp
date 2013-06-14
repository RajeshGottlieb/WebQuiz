<%@ include file="include/header.jsp"%>

<%@ page import="com.webquiz.model.Quiz" %>
<%@ page import="com.webquiz.model.Question" %>
<%@ page import="com.webquiz.model.Answer" %>

<h1>Quiz Results</h1>

<%
Quiz quiz = (Quiz) request.getSession().getAttribute("quiz"); 

if (quiz != null) {
    int quiz_correctCount = quiz.getCorrectCount();
    int quiz_questionCount = quiz.getQuestionCount();
%>
    <em><%=quiz_correctCount%> out of <%=quiz_questionCount%> correct</em>
    <hr>
<%    
    for (Question question : quiz.getQuestions()) {
        int question_id = question.getId();
        Question.Type question_type = question.getType();
        String question_text = question.getText();
%>
        <%=question_text%><br /> <br />
        Your answer:<br />
<%
        for (String userAnswer : question.getUserAnswers()) {
%>
           <%=userAnswer%><br /> 
<%
        }
%>
        <br />    
<%
        if (question.isAnsweredCorrectly()) {
%>
            <font color="green">Correct</font>
<%
        } else {
%>
            <font color="red">Wrong</font>
<%        
        }
%>
<hr>
<%
    }
}
%>

<%@ include file="include/footer.html"%>