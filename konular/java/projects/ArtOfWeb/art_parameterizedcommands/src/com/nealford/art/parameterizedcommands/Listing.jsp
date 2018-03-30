<%@ page import="java.util.*" %>
<jsp:useBean id="keywords"
             scope="request" class="java.util.List" />
<jsp:useBean id="proposed"
             scope="request" class="java.util.List" />
<HTML>
<HEAD>
<TITLE>
Listing
</TITLE>
</HEAD>
<BODY>

<hr>
<H1>
Listing of Java Keywords
</H1>
<hr>
<p>
<%
    Iterator it = keywords.iterator();
    while (it.hasNext()) {
%>
        <b><code><%= it.next() %></code></b><br>
<%
    }
%>
</p>
<hr>
<h2>
Proposed New Keywords
</h2>
<hr>
<p>
<%
    it = proposed.iterator();
    while (it.hasNext()) {
%>
        <b><code><%= it.next() %></code></b><br>
<%
    }
%>
</p>
<form method="post" action="main?cmd=formEntry">
<INPUT TYPE="SUBMIT" NAME="Add New Keyword" VALUE="Submit">
</FORM>
<hr>
</BODY>
</HTML>
