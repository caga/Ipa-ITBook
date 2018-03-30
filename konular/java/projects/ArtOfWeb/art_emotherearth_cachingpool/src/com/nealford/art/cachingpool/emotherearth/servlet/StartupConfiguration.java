package com.nealford.art.cachingpool.emotherearth.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.cachingpool.emotherearth.util.DBPool;
import java.sql.SQLException;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import com.nealford.art.cachingpool.emotherearth.util.
        KeyedBoundaryPoolFactory;

public class StartupConfiguration extends GenericServlet {
    private static final String DRIVER_CLASS = "driverClass";
    private static final String PASSWORD = "password";
    private static final String DB_URL = "dbUrl";
    private static final String USER = "user";
    private static final String CONNECTION_POOL = "dbPool";
    private static final String BOUNDARY_POOL = "boundaryPool";
    private static final String POOL_MAX_ACTIVE = "poolMaxActive";
    private static final String POOL_WHEN_EXHAUSTED =
            "poolWhenExhausted";

    public void init() throws javax.servlet.ServletException {
        String driverClass =
                getServletContext().getInitParameter(DRIVER_CLASS);
        String password =
                getServletContext().getInitParameter(PASSWORD);
        String dbUrl =
                getServletContext().getInitParameter(DB_URL);
        String user =
                getServletContext().getInitParameter(USER);
        DBPool dbPool =
                createConnectionPool(driverClass, password, dbUrl,
                user);
        getServletContext().setAttribute(CONNECTION_POOL, dbPool);

        GenericKeyedObjectPool boundaryPool = createBoundaryPool();
        getServletContext().setAttribute(BOUNDARY_POOL,
                boundaryPool);
    }

    private GenericKeyedObjectPool.Config getPoolConfiguration() {
        GenericKeyedObjectPool.Config conf =
                new GenericKeyedObjectPool.Config();
        conf.maxActive = Integer.parseInt(getServletContext().
                getInitParameter(POOL_MAX_ACTIVE));
        conf.whenExhaustedAction = Byte.parseByte(
                getServletContext().getInitParameter(
                POOL_WHEN_EXHAUSTED)) ;
        return conf;
    }

    private GenericKeyedObjectPool createBoundaryPool() {
        GenericKeyedObjectPool pool = null;
        try {
            pool = new GenericKeyedObjectPool(
                    new KeyedBoundaryPoolFactory());
            pool.setConfig(getPoolConfiguration());
        }
        catch (Throwable x) {
            System.out.println("Pool creation exception: " +
                               x.getMessage());
            x.printStackTrace();
        }
        return pool;
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



    public void service(ServletRequest req,
                        ServletResponse res)
            throws javax.servlet.ServletException,
                   java.io.IOException {
        //-- This method must be present because of the base class.
        //-- It is intentionally left blank in this servlet.
    }

}