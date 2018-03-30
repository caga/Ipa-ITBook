<%@ page import="com.nealford.art.history.customtags.stl.*" %><%@ page import="java.sql.*"%><%@ page contentType="text/html; charset=iso-8859-1" language="java"
    errorPage="GeneralErrorPage.jsp" %><%!
    private Order insertOrder(HttpServletRequest request,
                              HttpSession session,
                              String user,
                              ShoppingCart sc,
                              DbPool pool) {
        Order order = new Order();
        order.setDbPool(pool);
        order.setCcNum(request.getParameter("ccNum"));
        order.setCcType(request.getParameter("ccType"));;
        order.setCcExp(request.getParameter("ccExp"));

        try {
            order.addOrder(sc, user);
        } catch (SQLException sqlx) {
            getServletContext().log("Order insert error", sqlx);
        }
        return order;
    }
%><%
    DbPool dbPool = null;
    Connection connection = null;
    ShoppingCart cart =
            (ShoppingCart) session.getAttribute("shoppingCart");
    if (cart == null)
        throw new Exception("Nothing in shopping cart!");
    String userName = ((String) session.getAttribute("userName")).trim();
    try {
        dbPool = (DbPool)getServletContext().getAttribute("DbPool");
        connection = dbPool.getConnection();
        Order newOrder = insertOrder(request, session, userName,
                                     cart, dbPool);
%>
<html>
<head>
    <title>Confirmation</title>
</head>

<body>
    <h1><%= userName %>, thank you for shopping at
    eMotherEarth.com</h1>

    <h3>Your confirmation number is
    <%= newOrder.getOrderKey() %></h3>

    <p><a href="Welcome.html">Click here to return to the
    store</a></p><%
        } finally {
            dbPool.release(connection);
        }
    %>
</body>
</html>
