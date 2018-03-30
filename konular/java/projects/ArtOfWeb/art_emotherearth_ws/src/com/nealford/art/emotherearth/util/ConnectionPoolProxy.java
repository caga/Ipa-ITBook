package com.nealford.art.emotherearth.util;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ConnectionPoolProxy extends GenericServlet {
    static private ConnectionPoolProxy cpp;

    public void init() throws ServletException {
        cpp = this;
    }

    public void service(ServletRequest req, ServletResponse res)
            throws javax.servlet.ServletException,
            java.io.IOException {
        //-- intentionally left blank
    }

    public static ConnectionPoolProxy getInstance() {
        return cpp;
    }

    public DBPool getDbPool() {
        return (DBPool) getServletContext().getAttribute("dbPool");
    }

}