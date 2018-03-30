<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<jsp:useBean id="scheduleItems" scope="request"
        type="java.util.List" />

<html>
<head>
<title>
Schedule Items
</title>
</head>
<body>

<p><h2>Schedule List</h2></p>
<table border="2">

    <tr bgcolor="yellow">
        <c:forEach var="column" items="Start,Duration,Text,Event">
          <th><c:out value="${column}"/></th>
        </c:forEach>
    </tr>
    <tr>
  <c:forEach var="item" items="${scheduleItems}">
  <tr>
    <td><c:out value="${item.start}" /></td>
    <td><c:out value="${item.duration}" /></td>
    <td><c:out value="${item.text}" /></td>
    <td><c:out value="${item.eventType}" /></td>
  </tr>
  </c:forEach>
</table>
<p>

<a href="scheduleentry">Add New Schedule Item</a>

</body>
</html>