<%@page import="java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<html>
	<body>
		<br>
		<h2>		<%
		String bmi=(String)request.getAttribute("bmi");
		String name=request.getParameter("name");
		String surname=request.getParameter("surname");
		out.print("Merhaba "+name+" "+surname+"\n Vücut Kütle endeksin: "+bmi);
		System.out.println(request);
		%></h2>
	</body>
</html>
