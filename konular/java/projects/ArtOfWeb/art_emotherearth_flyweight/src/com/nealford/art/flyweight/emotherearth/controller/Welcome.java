package com.nealford.art.flyweight.emotherearth.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.flyweight.emotherearth.util.DBPool;
import java.sql.SQLException;
import com.nealford.art.flyweight.emotherearth.boundary.ProductDb;
import com.nealford.art.flyweight.emotherearth.util.IdComparator;

public class Welcome extends HttpServlet {

    public void init() throws ServletException {
        String driverClass =
                getServletContext().getInitParameter("driverClass");
        String password =
                getServletContext().getInitParameter("password");
        String dbUrl =
                getServletContext().getInitParameter("dbUrl");
        String user =
                getServletContext().getInitParameter("user");
        DBPool dbPool =
                createConnectionPool(driverClass, password, dbUrl,
                                     user);
        getServletContext().setAttribute("dbPool", dbPool);
        buildFlyweightReferences(dbPool);
    }

    private void buildFlyweightReferences(DBPool dbPool) {
        ProductDb productDb = (ProductDb) getServletContext().
                              getAttribute("products");
        if (productDb == null) {
            productDb = new ProductDb();
            productDb.setDbPool(dbPool);
            List productList = productDb.getProductList();
            Collections.sort(productList, new IdComparator());
            getServletContext().setAttribute("products",
                    productList);
        }
    }


    private DBPool createConnectionPool(String driverClass,
                                        String password,
                                        String dbUrl,
                                        String user) {
        DBPool dbPool = null;
        try {
            dbPool = new DBPool(driverClass, dbUrl, user, password);
        } catch (SQLException sqlx) {
            getServletContext().log(new java.util.Date() +
                                    ":Connection pool error", sqlx);
        }
        return dbPool;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        RequestDispatcher dispather =
                request.getRequestDispatcher("/WelcomeView.jsp");
        dispather.forward(request, response);
    }
}