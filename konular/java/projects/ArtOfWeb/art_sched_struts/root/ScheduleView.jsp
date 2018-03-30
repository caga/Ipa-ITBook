<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html>
<head>
<title>
<bean:message key="title.view" />
</title>
</head>
<body>
<h2><bean:message key="prompt.listTitle" /></h2></p>
<table border="2">
    <tr bgcolor="yellow">
        <th><bean:message key="prompt.start" /></th>
        <th><bean:message key="prompt.duration" /></th>
        <th><bean:message key="prompt.text" /></th>
        <th><bean:message key="prompt.eventType" /></th>
    </tr>

<logic:iterate id="schedItem"
        type="com.nealford.art.schedstruts.entity.ScheduleItem"
        name="scheduleBean" property="list" >
        <tr>
            <td><bean:write name="schedItem" property="start" />
            <td><bean:write name="schedItem"
                            property="duration" />
            <td><bean:write name="schedItem" property="text" />
            <td><bean:write name="schedItem"
                            property="eventType" />
        </tr>
</logic:iterate>
</table>
<p>

<a href="schedEntry.do"> Add New Schedule Item</a>

</body>
</html>
