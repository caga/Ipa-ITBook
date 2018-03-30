<%@ page import="com.borland.internetbeans.*,
        com.borland.dx.dataset.*,
        com.borland.dx.sql.dataset.*" %>
<%@ taglib uri="/internetbeans.tld" prefix="ix" %>

<ix:database id="scheduleDb" driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost/schedule" username="root"
    password="marathon">

<ix:query id="qryEvent"
    statement="select e.duration as 'Duration', et.event_text as
    'Event Type', e.start as 'Start', e.description as 'Description'
    from event e,  event_types et where e.event_type =
    et.event_type_key" >
<html>
<head>
<TITLE>
Schedule Items
</TITLE>
</HEAD>
<BODY>

<p><h2>Schedule List</h2></p>
<ix:table dataSet="qryEvent">
<table border="1">

    <tr bgcolor="yellow">
        <th>Duration</th>
        <th>Event Type</th>
        <th>Start</th>
        <th>Description</th>
    </tr>
    <tr>
    <td>5</TD>
    <td>Conference</td>
    <td>01-01-01</td>
    <td>Description</td>
  </tr>
</table>
</ix:table>
<p>

<a href="scheduleentry">Add New Schedule Item</a>
</ix:query>
</ix:database>

</body>
</html>
