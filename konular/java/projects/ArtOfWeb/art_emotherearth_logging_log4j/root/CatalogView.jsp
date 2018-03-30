<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%@ page import="java.util.*" %>
<%@ page
    import="com.nealford.art.logging.emotherearth.entity.Product" %>

<html>
<head>
<title>
CatalogView
</title>
</head>
<jsp:useBean id="product" scope="session"
         class="com.nealford.art.logging.emotherearth.entity.Product" />
<body>
<h1>
<%
    Integer start = (Integer) request.getAttribute("start");
    int s = start.intValue();
%>
Catalog of Items
</h1>
<TABLE border=1>
    <TR><TH><a href="catalog?sort=id&start=<%= s %>">ID</a>
    <TH><a href="catalog?sort=name&start=<%= s %>">NAME</a>
    <TH><a href="catalog?sort=price&start=<%= s %>">PRICE</a>
    <TH>Buy</TR>
    <c:forEach var="product" items="${outputList}">
    <TR>
        <TD><c:out value="${product.id}"/></td>
        <TD><c:out value="${product.name}"/></td>
        <TD align='right'>
            <c:out value="${product.priceAsCurrency}"/>
        </td>
        <TD>
            <form action="showcart" method="post">
                Qty: <input type="text" size="3" name="quantity">
                <input type="hidden" name="id"
                            value=<c:out value="${product.id}"/>>
                <input type="submit" value="Add to cart">
            </form>
        </TD>
    </TR>
    </c:forEach>
</TABLE>

<% pageContext.setAttribute("pageList",
        (String[]) request.getAttribute("pageList")); %>
<p> Pages: &nbsp;
<c:forEach var="page" items="${pageList}">
    <c:out value="${page}" escapeXml="false"/>
</c:forEach>
</body>
</html>
