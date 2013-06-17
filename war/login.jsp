<%@ include file="include/header.jsp"%>

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
    <br /> <input type="submit" name="action" value="REGISTER">
    
</form>

<%@ include file="include/footer.html"%>