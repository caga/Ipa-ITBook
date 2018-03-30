<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page
    import="com.nealford.art.logging.emotherearth.entity.Product" %>
<%@ page
    import="com.nealford.art.logging.emotherearth.entity.CartItem"%>
<html>
<head>
<title>
ShowCart
</title>
</head>
<jsp:useBean id="cart" scope="session"
  class="com.nealford.art.logging.emotherearth.util.ShoppingCart" />
<body>
<h1>
<%= session.getAttribute("user") %>, here is your cart:
</h1>
<%-- check for errors --%>
<%
    List errorList = (List) request.getAttribute("errorList");
    if (errorList != null)
        pageContext.setAttribute("errorList", errorList);
%>

<c:forEach var="error" items="${errorList}">
    <font color="red">
        <c:out value="${error}"/><br>
    </font>
</c:forEach>

<p>
<%
    pageContext.setAttribute("cartItems", cart.getItemList());
%>
<TABLE border=1>
    <TR>
        <c:forEach var="col" items="ID,NAME,PRICE,QUANTITY,TOTAL">
            <TH><c:out value="${col}"/>
        </c:forEach>
    </TR>
    <c:forEach var="cartItem" items="${cartItems}">
    <TR>
      <TD><c:out value="${cartItem.product.id}"/></td>
      <TD><c:out value="${cartItem.product.name}"/></td>
      <TD><c:out value="${cartItem.product.priceAsCurrency}"/></td>
      <TD><c:out value="${cartItem.quantity}"/></td>
      <TD><c:out value="${cartItem.extendedPriceAsCurrency}"/></td>
    </TR>
    </c:forEach>
    <TR>
        <TD>&nbsp;</td>
        <TD>&nbsp;</td>
        <TD>&nbsp;</td>
        <TD align='right'>Grand Total =</td>
        <TD align='right'><%= cart.getTotalAsCurrency() %></td>
    </TR>
</TABLE>

<P><a href="catalog"> Click here for more shopping </a></p>

<p>
<h3>Check out</h3>
<form action="checkout" method="post">
Credit Card # <input type="text" name="ccNum"><br>
Credit Card Type <select name="ccType">
<option value="Visa">Visa</option>
<option value="MC">MC</option>
<option value="Amex">Amex</option>
</select>
Credit Card Exp Date <input type="text" name="ccExp"><br>
<input type="submit" value="Check out">
</form>
</body>
</html>
