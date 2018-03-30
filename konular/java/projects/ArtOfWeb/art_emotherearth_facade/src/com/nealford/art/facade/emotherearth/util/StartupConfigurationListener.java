package com.nealford.art.facade.emotherearth.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import java.sql.SQLException;

public class StartupConfigurationListener implements
        ServletContextListener, AttributeConstants {

    public void contextInitialized(ServletContextEvent sce) {
        initializeDatabaseConnectionPool(sce.getServletContext());
        BoundaryFacade.getInstance().initiaizeBoundaryPool(
                sce.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void initializeDatabaseConnectionPool(
            ServletContext sc) {
        DBPool dbPool = null;
        try {
            dbPool = createConnectionPool(sc);
        } catch (SQLException sqlx) {
            sc.log(new java.util.Date() + ":Connection pool error",
                   sqlx);
        }

        sc.setAttribute(DB_POOL, dbPool);
    }

    private DBPool createConnectionPool(ServletContext sc)
            throws SQLException {
        String driverClass = sc.getInitParameter(DRIVER_CLASS);
        String password = sc.getInitParameter(PASSWORD);
        String dbUrl = sc.getInitParameter(DB_URL);
        String user = sc.getInitParameter(USER);

        DBPool dbPool = null;
        dbPool = new DBPool(driverClass, dbUrl, user, password);
        return dbPool;
    }
}