<%@ page import="java.util.*" %>

<jsp:useBean id="scheduleItem" scope="request"
        class="com.nealford.art.ejbsched.model.ScheduleItem" />
<jsp:useBean id="errors" scope="request" class="java.util.ArrayList"
        type="java.util.List" />
<jsp:useBean id="scheduleBean" scope="session"
        class="com.nealford.art.ejbsched.model.ScheduleBean" />
<jsp:setProperty name="scheduleItem" property="*" />

<HTML>
<HEAD>
<TITLE>
Add Schedule Item
</TITLE>
</HEAD>
<BODY>
<!-- img src="Schedule_Title.jpg" border="0" /-->
<!-- hr -->
<H3>
Add New Schedule Entry
</H3>
<!-- hr -->
<%-- handle errors that have come back --%>
<%
    if (errors != null && ! errors.isEmpty()) {
%>

        <hr>
        <b><u>Validation Errors</u><//b3><br>
        <font color="red">

<%
        //-- show list of validation errors
        Iterator it = errors.iterator();
        while (it.hasNext()) {
            String error = (String) it.next();
            out.println(error + "<br>");
        }
%>
</font>
<hr>
<%
    }
%>
<!-- Data entry form -->
<form action="SaveEntry" method="post">
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
            Map eventMap = scheduleBean.getEventTypes();
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

</BODY>
</HTML>
