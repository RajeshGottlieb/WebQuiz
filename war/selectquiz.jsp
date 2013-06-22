<jsp:include page="include/header.jsp" />

<%@ page import="com.webquiz.model.QuizSelection" %>
<%@ page import="com.webquiz.model.Category" %>
<%@ page import="com.webquiz.model.Subject" %>
<%@ page import="com.webquiz.model.Module" %>

<h1>Select the modules you want to be quizzed on</h1>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<p class="error"><%=error%></p>
<%
    }
%>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="GENERATE_QUIZ">
<%
QuizSelection selection = (QuizSelection) request.getAttribute("selection"); 

if (selection != null) {
%>
    <ul>
<%
    for (Subject subject : selection.getSubjects()) {
        String subject_name = subject.getName();
%>
        <li><h2><%=subject_name%></h2></li>
        <ul>
<%
        for (Category category : subject.getCategories()) {
            String category_name = category.getName();
%>
            <li><h3 class="pl30"><%=category_name%></h3></li>
            <ul class="pb20">
<%
            for (Module module : category.getModules()) {
                int module_id = module.getId();
                String module_name = module.getName();
%>
                <li class="pl60"><input type="checkbox" name="module" value="<%=module_id%>"><%=module_name%></li>
<%
            }
%>
            </ul>
<%
        }
%>
        </ul>
<%
    }
%>
    </ul>
<%
}
%>
<br /> <input type="submit" value="Submit">
</form>

<%@ include file="include/footer.jsp"%>