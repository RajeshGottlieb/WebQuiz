<%@ include file="include/header.jsp"%>

<%@ page import="com.webquiz.model.QuizSelection" %>
<%@ page import="com.webquiz.model.Category" %>
<%@ page import="com.webquiz.model.Subject" %>
<%@ page import="com.webquiz.model.Module" %>

<h1>Select the modules you want to be quizzed on</h1>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<em><font color="red"><%=error%></font></em>
<%
    }
%>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="SELECT_QUIZ">
<%
QuizSelection selection = (QuizSelection) request.getAttribute("selection"); 

if (selection != null) {
%>
    <ul>
<%
    for (Subject subject : selection.getSubjects()) {
        String subject_name = subject.getName();
%>
        <li><%=subject_name%></li>
        <ul>
<%
        for (Category category : subject.getCategories()) {
            String category_name = category.getName();
%>
            <li><%=category_name%></li>
            <ul>
<%
            for (Module module : category.getModules()) {
                int module_id = module.getId();
                String module_name = module.getName();
%>
                <li><input type="checkbox" name="module" value="<%=module_id%>"><%=module_name%></li>
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

<%@ include file="include/footer.html"%>