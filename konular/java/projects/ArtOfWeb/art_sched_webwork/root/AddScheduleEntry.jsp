<%@ taglib uri="webwork" prefix="ww" %>
<HTML>
<HEAD>
<TITLE>
<ww:text name="'view.title'"/>
</TITLE>
</HEAD>
<BODY>
<H3>
<ww:text name="'view.title'"/>
</H3>
<form action="scheduleentry.action" method="post">
<table border="0" width="30%" align="left">
    <tr><td>
    <ww:textfield label="text('input.duration')"
                  name="'duration'"
                  value='<webwork:property value="duration"/>' />
    </td></tr>
    <tr><td>
    <ww:select label="text('input.eventType')"
               name="'eventType'"
               list="events"
               listKey="'key'"
               listValue="'event'"/>
    </td></tr>
    <tr><td>
    <ww:textfield label="text('input.start')"
                  name="'start'"
                  value='<webwork:property value="start"/>' />
    </td></tr>
    <tr><td>
    <ww:textfield label="text('input.text')"
                  name="'text'"
                  value='<webwork:property value="text"/>' />
    </td></tr>
    <tr><td align="right">
    <input type="submit" name="Submit"
               value="<ww:text name="'input.submit'"/>">
    </td></tr>
</table>
</form>

</BODY>
</HTML>
