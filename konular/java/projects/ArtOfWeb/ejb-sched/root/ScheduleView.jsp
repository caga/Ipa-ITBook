<%@ page import="java.util.*" %>
<%@ page import="com.nealford.art.ejbsched.model.ScheduleItem"%>

<jsp:useBean id="scheduleBean" scope="request"
        class="com.nealford.art.ejbsched.model.ScheduleBean" />

<HTML>
<HEAD>
<TITLE>
Schedule Items
</TITLE>
</HEAD>
<BODY>
<!-- img src="Schedule_Title.jpg" border="0" />
<H1>
Schedule Items
</H1>
<hr -->

<p><h2>Schedule List</h2></p>
<table border="2">
    <tr bgcolor="yellow">
        <th>Start</th>
        <th>Duration</th>
        <th>Text</th>
        <th>Event Type</th>
    </tr>
<%
    Iterator it = scheduleBean.getScheduleItems().iterator();
    while (it.hasNext()) {
        pageContext.setAttribute("item", (ScheduleItem) it.next());
%>
        <tr>
            <td><jsp:getProperty name="item" property="start"/>
            <td align="center"><jsp:getProperty name="item"
                    property="duration"/>
            <td><jsp:getProperty name="item" property="text"/>
            <td><jsp:getProperty name="item" property="eventType"/>
        </tr>
<%
    }
%>
</table>
<p>

<a href="ScheduleEntry">Add New Schedule Item</a>

</BODY>
</HTML>