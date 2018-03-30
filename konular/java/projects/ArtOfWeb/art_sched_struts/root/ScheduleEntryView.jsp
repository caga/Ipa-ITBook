<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title><bean:message key="title.add" /></title>
</head>
<body>
<h3><bean:message key="prompt.addEventTitle" /></h3>
<logic:messagesPresent>
    <h3><font color="red">
        <bean:message key="errors.header"/>
    </font></h3>
    <ul>
        <html:messages id="error">
            <li><bean:write name="error"/></li>
        </html:messages>
    </ul>
    <p/>
</logic:messagesPresent>

<html:form action="add.do">
<table border="0" width="30%" align="left">
  <tr>
    <th align="right">
      <bean:message key="prompt.duration"/>
    </th>
    <td align="left">
      <html:text property="duration" size="16"/>
    </td>
  </tr>
  <tr>
    <th align="right">
      <bean:message key="prompt.eventType"/>
    </th>
    <td align="left">
      <html:select property="eventTypeKey">
        <html:options collection="eventTypes" property="value"
                      labelProperty="label"/>
      </html:select>

    </td>
  </tr>
  <tr>
    <th align="right">
      <bean:message key="prompt.start"/>
    </th>
    <td align="left">
      <html:text property="start" size="16"/>
    </td>
  </tr>
  <tr>
    <th align="right">
      <bean:message key="prompt.text"/>
    </th>
    <td align="left">
      <html:text property="text" size="16"/>
    </td>
  </tr>

  <tr>
    <td align="right">
        <html:submit>
            <bean:message key="button.submit"/>
        </html:submit>
    </td>
    <td align="right">
        <html:reset>
            <bean:message key="button.reset"/>
        </html:reset>
    </td>
  </tr>
</table>
</html:form>

</body>
</html>
