<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0"
    prefix="req" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/session-1.0"
    prefix="sess" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%@ taglib uri="http://com.nealford.art.emotherearth"
    prefix="emotherearth" %>

<%@ page import="com.nealford.art.history.customtags.stl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.text.NumberFormat"%>

<emotherearth:addDbPoolToApplication  initUserName="user"
    initPasswordName="password" initUrlName="dbUrl"
    initDriverClassName="driverClass" />

<sess:existsAttribute name="userName" value="false">
  <sess:setAttribute name="userName">
    <req:parameter name="userName"/>
  </sess:setAttribute>
</sess:existsAttribute>

<%@ page contentType="text/html; charset=iso-8859-1" language="java"
         errorPage="GeneralErrorPage.jsp" %>
<head>

<title>Catalog</title>
</head>
<body>
<h3>Hello, <sess:attribute name="userName"/>
    . Welcome back to the store!</h3>

<h1>Products </h1>
<emotherearth:htmlSqlResult dbPool="DbPool"
  sql="SELECT * FROM PRODUCTS" formActionDestination="ShowCart.jsp"
  shoppingForm="true" />
<p>&nbsp;</p>
</body>
</html>
