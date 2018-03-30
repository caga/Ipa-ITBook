<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- JSTL tag libs --%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld" %>

<%-- Struts provided Taglibs --%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<html:html locale="true"/>
<head>
	<fmt:setBundle basename="ApplicationResources" />
	<title><fmt:message key="loggedin.title"/></title>
</head>
<body>
	<H2>
	<fmt:message key="loggedin.msg">
		<fmt:param value='${requestScope.userName}' />
	</fmt:message>
	</H2>
</body>
</html>
