<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%= application.getInitParameter("htmlDocType") %>
<html>

<jsp:include page="include/head.jsp" />
<body id="register">
<jsp:include page="include/header.jsp" />

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<h1>Register with <%= application.getInitParameter("siteName") %></h1>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<em><font color="red"><%=error%></font></em>
<%
    }
%>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="REGISTER"> <br> <br>
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
    <br /> <input type="submit" name="Register" value="REGISTER">
    
</form>
</div>              <!-- //// End Content ////////////////////////////////////////////////////////////////////////// -->

<%@ include file="include/footer.jsp" %>
</body>
</html>