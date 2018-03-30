<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="scheduleItem" scope="request"
        class="com.nealford.art.mvcsched.entity.ScheduleItem" />
<jsp:useBean id="errors" scope="request" class="java.util.ArrayList"
        type="java.util.List" />
<jsp:useBean id="scheduleDb" scope="page"
        class="com.nealford.art.mvcsched.boundary.ScheduleDb" />
<jsp:setProperty name="scheduleItem" property="*" />

<html>
<head>
<title>
Add Schedule Item
</title>
</head>
<body>
<h3>
Add New Schedule Entry
</h3>
<c:if test="${! empty errors}">
    <hr/>
    <b><u>Validation Errors</u><//b3><br>
    <font color="red">

    <c:forEach var="error" items="${errors}">
        <c:out value="${error}" /><br>
    </c:forEach>
    </font>
    <hr/>
</c:if>
<!-- Data entry form -->
<form action="saveentry" method="post">
<table border="0" width="30%" align="left">
  <tr>
    <th align="right">
      Duration
    </th>
    <td align="left">
    <input name="duration" size="16"
        value="<jsp:getProperty name="scheduleItem"
                property="duration"/>">
    </td>
  </tr>
  <tr>
    <th align="right">
      Event Type
    </th>
    <td align="left">
      <select name="eventTypeKey">
<%
          //-- get the list of allowable event types from bean
            int currentValue = scheduleItem.getEventTypeKey();
            Map eventMap = scheduleDb.getEventTypes();
            Set keySet = eventMap.keySet();
            Iterator eti = keySet.iterator();
            while (eti.hasNext()) {
                int key = ((Integer) eti.next()).intValue();
%>
                <option value='<%= key %>'<%= (currentValue == key ?
                    "selected" : "") + ">" +
                    eventMap.get(new Integer(key)) %>
<%
            }
%>
      </select>
    </td>
  </tr>
  <tr>
    <th align="right">
      Start
    </th>
    <td align="left">
      <input name="start" size="16" value="<jsp:getProperty
            name="scheduleItem" property="start"/>"/>
    </td>
  </tr>
  <tr>
    <th align="right">
      Text
    </th>
    <td align="left">
      <input name="text" size="16"	value="<jsp:getProperty
            name="scheduleItem"	property="text"/>"/>
    </td>
  </tr>

  <tr>
    <td align="right">
        <input type="submit" name="Submit" value="Submit">
    </td>
    <td align="right">
        <input type="reset" value="Reset">
    </td>
  </tr>
</table>
</form>

</body>
</html>
