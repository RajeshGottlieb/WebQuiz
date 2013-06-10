<%@ include file="include/header.jsp"%>

<h1>Login to Web Quiz</h1>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<em><font color="red"><%=error%></font></em>
<%
    }
%>

<form method="post" action="Login">
    <br> <br> Please enter your User Name and Password.<br>
    <br>
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
    <br /> <input type="submit" name="Submit" value="Submit">
</form>

<%@ include file="include/footer.html"%>