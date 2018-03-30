<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- JSTL tag libs --%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld" %>

<%-- Struts provided Taglibs --%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html-el.tld" %>

<html:html locale="true"/>
<head>
	<fmt:setBundle basename="ApplicationResources" />
	<title><fmt:message key="login.title"/></title>
</head>
<body>
<html:errors property="login"/>
<html:form action="login.do" focus="userName">
	<table align="center">
  		<tr align="center">
    		<td><H1><fmt:message key="login.message"/></H1></td>
  		</tr>
  		<tr align="center">
			<td>
  				<table align="center">
	  				<tr>
	    				<td align="right">
							<fmt:message key="login.username"/>
						</td>
	   					<td align="left">
							<html:text 	property="userName" 
	    								size="15" 
	    								maxlength="15" />
							<html:errors property="userName" />
						</td>
	  				</tr>	
	  				<tr>
	   					<td align="right">
							<fmt:message key="login.password"/>
						</td>
	   					<td align="left">
							<html:password 	property="password" 
	    									size="15" 
	    									maxlength="15" 
	    									redisplay="false"/>
							<html:errors property="password" />
						</td>
	 				</tr>	
	 				<tr>
						<td colspan="2" align="center">
							<html:submit>
								<fmt:message key="login.button.signon"/>
							</html:submit>
						</td>
	  				</tr>
  				</table>
			</td>
  		</tr>
	</table>
</html:form>
</body>
</html>
