<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= application.getInitParameter("siteName") %></title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/site.css" />
<link rel="stylesheet" type="text/css" href="css/page.css" />
</head>
<body>
<%@ page import="com.webquiz.model.User" %>

<%
User user = (User) request.getSession().getAttribute("user");
%>

<div id="header" class="bgOne">  <!-- //// Start Navigation ////////////////////////////////////////////////////////// -->
    <ul id="nav" >
        <li id="logo" class="fl"><h1><%= application.getInitParameter("siteName") %></h1></li>
        <li class="fl hoverA"><a href="/WebQuiz/Servlet?action=ABOUT">About</a></li>
<% if (user != null) { 
	   String username = user.getUsername();
%>
        <li class="fr hoverA"><a href="/WebQuiz/Servlet?action=LOGOUT">Logout</a></li>
        <li class="fr pr10">Welcome <%= username %></li>
<% } else { %>
        <li class="fr hoverA"><a href="/WebQuiz/">Login</a></li>
<% } %>
    </ul>
</div>                        <!-- //// End Navigation //////////////////////////////////////////////////////////// -->

<div id="content">  <!-- //// Start Content /////////////////////////////////////////////////////////////////////// -->
