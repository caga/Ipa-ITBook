package com.nealford.art.logging.emotherearth.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.logging.emotherearth.util.DBPool;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Welcome extends HttpServlet {
    private static Logger logger = Logger.getLogger(
            Welcome.class.getPackage().getName().substring(0,
            Welcome.class.getPackage().getName().lastIndexOf('.')));

    private void setupLogger() {
        Handler outputHandler = null;
        try {
            outputHandler = new FileHandler("/temp/basic_log.xml");
        } catch (Exception x) {
            logger.severe("Can't create handler: "+ x.getMessage());
        }
        if (outputHandler != null)
            logger.addHandler(outputHandler);
        String logLevel = getServletContext().
                          getInitParameter("logLevel");
        if (logLevel != null)
            logger.setLevel(Level.parse(logLevel.toUpperCase()));
        else
            logger.setLevel(Level.SEVERE);
    }

    public void init() throws ServletException {
        setupLogger();
        logger.entering(this.getClass().getName(), "init()");
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
        logger.exiting(this.getClass().getName(), "init()");
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
    }
}