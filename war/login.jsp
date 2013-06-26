<jsp:include page="include/header.jsp" />

<h1>Login to <%= application.getInitParameter("siteName") %></h1>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<p><em class="error"><%=error%></em></p>
<%
    }
%>

<form method="post" action="Servlet">
    Please enter your User Name and Password.<br> <br>
    <table cellpadding='4' cellspacing='2'>
        <tr>
            <td>User Name:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"></td>
        </tr>
    </table>
    <br /> <input type="submit" name="action" value="LOGIN">
</form>
    <br />
    <br /> Please <a href="/WebQuiz/Servlet?action=NEWUSER">Register as a new user</a> if you do not have an account. 

<%@ include file="include/footer.jsp"%>