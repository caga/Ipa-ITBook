<%@ taglib uri="http://jakarta.apache.org/taglibs/session-1.0"
        prefix="sess" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri='http://java.sun.com/jstl/fmt'  prefix='fmt'%>
<%@ page import="com.nealford.art.history.customtags.stl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.text.NumberFormat"%>

<%!
    static final private String SQL_GET_PRODUCT =
            "select * from products where id = ?";

    private ShoppingCart getCart(HttpSession session) {
        ShoppingCart cart =
                (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart == null)
            cart = new ShoppingCart();
        return cart;
    }

    private boolean addItemToCart(Connection c,
                                  int itemId,
                                  int quantity,
                                  ShoppingCart sc)
            throws SQLException {
        PreparedStatement ps = c.prepareStatement(SQL_GET_PRODUCT);
        ps.setInt(1, itemId);
        ResultSet rs = ps.executeQuery();
        boolean status;
        if (status = rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            ShoppingCartItem sci = new ShoppingCartItem(id, name,
                    quantity, price);
            sc.addItem(sci);
        }
        return status;
    }
%>
<%
    DbPool dbPool = null;
    Connection connection = null;
    ShoppingCart cart = getCart(session);
    String userName = (String) session.getAttribute("userName");
    int itemId = Integer.parseInt(request.getParameter("id"));
    int quantity = Integer.parseInt(
                request.getParameter("quantity"));
    try {
        dbPool = (DbPool)getServletContext().getAttribute("DbPool");
        connection = dbPool.getConnection();
%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
         errorPage="GeneralErrorPage.jsp" %>
<html>
<head>
<title>Shopping Cart</title>
</head>

<body>
<%
    if (! addItemToCart(connection, itemId, quantity, cart)) {
%>
        Error! Could not add item to cart!
<%
    }
    pageContext.setAttribute("items", cart.getItemList());
%>
<h3>
    <sess:attribute name="userName"/>, here is your shopping cart:
</h3>

<TABLE border="1">
  <TR>
  <c:forEach var="column" items="ID,Name,Quantity,Price,Total">
    <th><c:out value="${column}"/></th>
  </c:forEach>
  </TR>

  <c:forEach var="cartItem" items="${items}">
  <TR>
    <TD><c:out value="${cartItem.itemId}" /></TD>
    <TD><c:out value="${cartItem.itemName}" /></TD>
    <TD><c:out value="${cartItem.quantity}" /></TD>
    <fmt:formatNumber type="currency" var="itemPriceAsCurrency"
            value="${cartItem.itemPrice}"/>
    <fmt:formatNumber type="currency" var="extendedPriceAsCurrency"
            value="${cartItem.extendedPrice}"/>
    <TD align='right'>
        <c:out value="${itemPriceAsCurrency}" />
    </TD>
    <TD align='right'>
        <c:out value="${extendedPriceAsCurrency}" />
    </TD>
  </TR>
  </c:forEach>
</TABLE>

<p><a href="Catalog.jsp">Click here to return to the store</a> </p>

<h3>Check out</h3>
<form method="post" action="Confirmation.jsp">
  <p>Credit Card #
    <input type="text" name="ccNum">
  </p>
  <p>Credit Card Type
    <select name="ccType">
      <option value="Amex">Amex</option>
      <option value="Visa">Visa</option>
      <option value="MC">MC</option>
    </select>
    Credit Card Exp Date:
    <input type="text" name="ccExp">
  </p>
  <p>
    <input type="submit" name="Submit" value="Check out">
  </p>
</form>
</body>
</html>

<%
    session.setAttribute("shoppingCart", cart);
    } finally {
      dbPool.release(connection);
    }
%>
