<%@ page isErrorPage="true" %>
<html>
<body bgcolor="#00ffff">

<h1>Error page CheckOutView</h1>

<br>An error occured in the bean. Error Message is: <%= exception.getMessage() %><br>
Stack Trace is : <pre><font color="red"><% 
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter(); 
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true); 
 exception.printStackTrace(pw); 
 out.println(cw.toString()); 
 %></font></pre>
<br></body>
</html>
