<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>${initParam.htmlDocType}
<html>

<jsp:include page="include/head.jsp" />
<body id="register">
<jsp:include page="include/header.jsp" />

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<h1>Register with ${initParam.siteName}</h1>

<c:if test="${!(empty error)}">
    <p class="error">${error}</p>
</c:if>

<form method="post" action="Servlet">
    <input type="hidden" name="action" value="REGISTER"> <br> <br>
    Please enter your User Name and Password.<br> <br>
    <table cellpadding='4' cellspacing='2'>
        <tr>
            <td>User Name:</td>
            <td><input id="username" type="text" name="username"></td>
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