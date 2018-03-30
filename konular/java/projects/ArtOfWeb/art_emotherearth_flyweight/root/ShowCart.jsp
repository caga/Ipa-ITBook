<%@ page import="java.util.*" %>
<%@ page
  import="com.nealford.art.flyweight.emotherearth.entity.Product" %>
<%@ page
  import="com.nealford.art.flyweight.emotherearth.entity.CartItem"%>
<html>
<head>
<title>
ShowCart
</title>
</head>
<jsp:useBean id="cart" scope="session"
 class="com.nealford.art.flyweight.emotherearth.util.ShoppingCart"/>
<body>
<h1>
<%= session.getAttribute("user") %>, here is your cart:
</h1>
<%-- check for errors --%>
<%
    Vector errorList = (Vector) request.getAttribute("errorList");
    if (errorList != null) {
%>
        <hr>
        <font color="red">
<%
        for (int i = 0; i < errorList.size(); i++) {
            out.println(errorList.elementAt(i) + "<br>");
        }
%>
        </font>
        <hr>
<%
    }
%>
<p>
<TABLE border=1>
<TR><TH>ID<TH>NAME<TH>PRICE<TH>QUANTITY<TH>TOTAL</TR>
<%
    Iterator iterator = cart.getItemList().iterator();
    while (iterator.hasNext()) {
        CartItem ci = (CartItem) iterator.next();
        pageContext.setAttribute("ci", ci);
        Product p = ci.getProduct();
        pageContext.setAttribute("p", p);
%>
<TR><TD><jsp:getProperty name="p" property="id" /></td>
<TD><jsp:getProperty name="p" property="name" /></td>
<TD align='right'><jsp:getProperty name="p" property="priceAsCurrency" /></td>
<TD align='right'><jsp:getProperty name="ci" property="quantity" /></td>
<TD align='right'><jsp:getProperty name="ci" property="extendedPriceAsCurrency" /></td>
</TR>
<%
    }
%>
<TR><TD>&nbsp;</td><TD>&nbsp;</td><TD>&nbsp;</td>
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
