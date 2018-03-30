package com.nealford.art.schedtapestry.util;

import java.io.*;
import com.nealford.art.schedtapestry.boundary.*;
import java.sql.*;

public class Visit implements Serializable {
    private String driverClass;
    private String dbUrl;
    private String user;
    private String password;
    private ScheduleDb scheduleDb;

    public Visit() {
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ScheduleDb getScheduleDb() throws ScheduleException {
        if (scheduleDb == null) {
            scheduleDb = new ScheduleDb(driverClass, dbUrl, user,
                                        password);
            scheduleDb.populate();
        }
        return scheduleDb;
    }

}