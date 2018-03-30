<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="keywords"
             scope="request" class="java.util.List" />
<jsp:useBean id="proposed"
             scope="request" class="java.util.List" />
<html>
<head>
<title>Listing</title>
</head>
<body>
<hr><h1>Listing of Java Keywords</h1><hr>
<p><c:forEach var="keyword" items="${keywords}">
      <b><code><c:out value="${keyword}"/></code></b><br>
</c:forEach></p>
<hr><h2>Proposed New Keywords</h2><hr>
<p><c:forEach var="propKeyword" items="${proposed}">
      <b><code><c:out value="${propKeyword}"/></code></b><br>
</c:forEach></p>
<form method="post" action="controller?cmd=formEntry">
<input type="submit" name="Add Keyword" value="Add New Keyword">
</form>
<hr>
</body>
</html>
