package com.nealford.art.caching.emotherearth.servlet;

import com.nealford.art.caching.emotherearth.boundary.ProductDb;
import com.nealford.art.caching.emotherearth.util.DBPool;
import com.nealford.art.caching.emotherearth.util.ProductCache;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        getServletContext().setAttribute("productCache",
                buildProductCache(dbPool));

    }

    private ProductCache buildProductCache(DBPool dbPool) {
        ProductDb productDb = new ProductDb();
        ProductCache productCache = new ProductCache();
        productCache.setProductCache(
                productDb.getProductList(dbPool));
        return productCache;
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
                      HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispather =
                request.getRequestDispatcher("/WelcomeView.jsp");
        dispather.forward(request, response);
    }}