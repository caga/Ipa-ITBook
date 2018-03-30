<%@ taglib uri="webwork" prefix="webwork" %>

<html>
<head>
<title>
    <webwork:text name="'view.title'"/>
</title>
</head>
<body>
<h3><webwork:text name="'view.title'"/></h3>
<table border="1" >
    <tr>
        <webwork:iterator value="columns">
            <th><webwork:property/></th>
        </webwork:iterator>
    </tr>

    <webwork:iterator value="scheduleItems" >
    <tr>
        <td><webwork:property value="start"/></td>
        <td align="center"><webwork:property value="duration"/></td>
        <td><webwork:property value="text"/></td>
        <td><webwork:property value="eventType"/></td>
    </tr>
    </webwork:iterator>
</table>
<p><a href="<webwork:url value="'addscheduleitem.action'"/>">
        <webwork:text name="'view.addlink'"/></a></p>
</body>
</html>