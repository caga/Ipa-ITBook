package com.nealford.art.history.servletemotherearth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.nealford.art.history.servletemotherearth.lib.*;

public class ShowCart extends HttpServlet {
    static final private String CONTENT_TYPE = "text/html";
    static final private String SQL_GET_PRODUCT =
            "select * from products where id = ?";
    private Connection con;


    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = generatePagePrelude(response);

        HttpSession session = getSession(request, out, response);
        if (session == null)
            return;

        String userName =
                session.getAttribute("user").toString();
        ShoppingCart sc = getShoppingCart(session);
        out.println("<h3>" + userName +
                    ", here is your shopping cart:</h3>");

        int itemId = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(
                request.getParameter("quantity"));
        Connection con = null;
        DbPool pool =
                (DbPool) getServletContext().getAttribute("DbPool");
        if (pool == null) {
            getServletContext().log("Error retrieving pool");
            return;
        }
        try {
            con = pool.getConnection();
            if (! addItemToCart(con, itemId, quantity, sc))
                out.println("Error: Failed to add item to cart");
        } catch (SQLException sqlx) {
            getServletContext().log("SQL error adding item:", sqlx);
        } finally {
            pool.release(con);
        }

        out.println(sc.toHtmlTable());
        session.setAttribute("cart", sc);
        outputCheckoutForm(userName, out);
        generatePagePostlude(out);
    }

    private void generatePagePostlude(PrintWriter out) {
        out.println("</body></html>");
    }

    private void outputCheckoutForm(String user, PrintWriter out) {
        out.println("<p><p><a href=\"catalog?username=" + user +
                    "\"> Click here to return to catalog</a>");
        out.println("<p>");
        out.println("<h3>Check out</h3>");
        out.println("<form action='confirmation' method='post'>");
        out.println("Credit Card # <input type='text' " +
                    "name='ccNum'><br>");
        out.println("Credit Card Type <select name='ccType'>");
        out.println("<option value='Visa'>Visa</option>");
        out.println("<option value='MC'>MC</option>");
        out.println("<option value='Amex'>Amex</option>");
        out.println("</select>");
        out.println("Credit Card Exp Date <input type='text' " +
                    "name='ccExp'><br>");
        out.println("<input type='submit' value='Check out'>");
        out.println("</form>");
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

    private ShoppingCart getShoppingCart(HttpSession session) {
        ShoppingCart sc =
                (ShoppingCart) session.getAttribute("cart");
        if (sc == null) {
            sc = new ShoppingCart();
        }
        return sc;
    }

    private HttpSession getSession(HttpServletRequest request,
                                   PrintWriter out,
                                   HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("Welcome.html");
        }
        return session;
    }

    private PrintWriter generatePagePrelude(
            HttpServletResponse response)
            throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>ShowCart</title></head>");
        out.println("<body>");
        return out;
    }

    public void destroy() {
    }

}