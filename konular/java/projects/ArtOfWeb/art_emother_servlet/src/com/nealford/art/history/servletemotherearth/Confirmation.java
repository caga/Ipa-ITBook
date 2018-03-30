package com.nealford.art.history.servletemotherearth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.nealford.art.history.servletemotherearth.lib.*;

public class Confirmation extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";

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
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/Welcome.html");
            return;
        }

        String user = (String) session.getAttribute("user");
        ShoppingCart sc = (ShoppingCart) session.getAttribute("cart");
        DbPool dbPool =
                (DbPool) getServletContext().getAttribute("DbPool");

        //-- insert order
        Order order = insertOrder(request, session, response, out, user,
                                  sc, dbPool);
        if (order == null)
            return;

        generateConfirmation(out, user, order.getOrderKey());

        session.invalidate();
    }

    private void generateConfirmation(PrintWriter out, String user,
                                      int orderKey) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("CheckOutView");
        out.println("</title>");
        out.println("</head>");
        out.println("<h1>");
        out.println(user + ", thank you for shopping at " +
                    "eMotherEarth.com");
        out.println("</h1>");
        out.println("<h3>");
        out.println("Your confirmation number is " + orderKey);
        out.println("</h3>");
        out.println("<p>");
        out.println("<P>");
        out.println("<a href='Welcome.html'> " +
                    "Click here to return to the store</a>");
        out.println("</P>");
        out.println("</P>");
        out.println("</body>");
        out.println("</html>");
    }



    private Order insertOrder(HttpServletRequest request,
                              HttpSession session,
                              HttpServletResponse response,
                              PrintWriter out,
                              String user,
                              ShoppingCart sc,
                              DbPool pool)
            throws IOException{
        Order order = new Order();
        order.setDbPool(pool);
        //-- get request parameters
        String ccNum = request.getParameter("ccNum");
        String ccType = request.getParameter("ccType");
        String ccExp = request.getParameter("ccExp");

        try {
            order.addOrder(sc, user, ccNum, ccType, ccExp);
        } catch (SQLException sqlx) {
            getServletContext().log("Order insert error", sqlx);
        }
        return order;

    }

    public void destroy() {
    }
}