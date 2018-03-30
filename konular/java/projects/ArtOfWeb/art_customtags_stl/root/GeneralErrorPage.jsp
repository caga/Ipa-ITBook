<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="generator" content=
    "HTMLTrim (vers 1st October 2003), see http://htmltrim.sourceforge.net" />

    <title></title>
</head>

<body>
    <h1>Error page for eMotherEarth.com</h1><br />
    An error occured in the page. The error Message is:
    <%= exception.getMessage() %><br />
    Stack Trace is :
    <pre>
<font color="red"><%
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
 exception.printStackTrace(pw);
 out.println(cw.toString());
 %></font>
</pre><br />
</body>
</html>
