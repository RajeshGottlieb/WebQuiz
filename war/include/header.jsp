<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Web Quiz</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/site.css" />
</head>
<body>
<%@ page import="com.webquiz.model.User" %>

<%
User user = (User) request.getSession().getAttribute("user");
%>

<div id="nav" class="bgOne">  <!-- //// Start Navigation ////////////////////////////////////////////////////////// -->
    <ul>
        <li id="logo" class="fl"><h1><a href="home.jsp">Web Quiz</a></h1></li>
        <li class="fl hoverA"><a href="/about">About</a></li>
<% if (user != null) { 
	   String username = user.getUsername();
%>
        <li class="fr hoverA"><a href="/WebQuiz/Servlet?action=LOGOUT">Logout</a></li>
        <li class="fr">Welcome <%= username %></li>
<% } %>
    </ul>
</div>                        <!-- //// End Navigation //////////////////////////////////////////////////////////// -->

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
