package com.nealford.art.logging.emotherearth.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.logging.emotherearth.util.DBPool;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;

public class Welcome extends HttpServlet {
    static Logger logger = Logger.getLogger(
            Welcome.class.getPackage().getName().substring(0,
            Welcome.class.getPackage().getName().lastIndexOf('.')));

    private void setupLogger() {
        Appender outputAppender = null;
        try {
            outputAppender = new FileAppender(new XMLLayout(),
                    "/temp/log4j_basic.xml");
        } catch (IOException x) {
            logger.error("Appender error", x);
        }
        logger.addAppender(outputAppender);

        String logLevel = getServletContext().
                          getInitParameter("logLevel");
        if (logLevel != null)
                    logger.setLevel(Level.toLevel(logLevel));
        else
            logger.setLevel(Level.FATAL);
    }

    public void init() throws ServletException {
        setupLogger();
        logger.info(this.getClass().getName() + ":init() entry");
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
        logger.info(this.getClass().getName() + ":init() exit");
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