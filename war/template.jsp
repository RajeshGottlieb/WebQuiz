<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%= application.getInitParameter("htmlDocType") %>
<html>
<jsp:include page="include/head.jsp" />
<body>
<jsp:include page="include/header.jsp" />
<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
<h1>Replace with Content</h1>
</div>              <!-- //// End Content ////////////////////////////////////////////////////////////////////////// -->
<%@ include file="include/footer.jsp" %>
</body>
</html>