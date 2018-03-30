package com.nealford.art.schedwebwork.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.nealford.art.schedwebwork.boundary.ScheduleDb;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import webwork.action.ActionContext;
import webwork.action.ActionSupport;

public class ViewSchedule extends ActionSupport {
    private static final Logger logger = Logger.getLogger(
            ViewSchedule.class);
    private String driverClass;
    private String dbUrl;
    private String user;
    private String password;
    private ScheduleDb scheduleDb;
    private List scheduleItems;
    private String[] columns;
    private Map eventTypes;

    static {
        try {
            logger.addAppender(new FileAppender(new SimpleLayout(),
                    "c:/temp/sched-webwork.log"));
        } catch (IOException ex) {
            logger.error("Can't create log file");
        }
    }

    private void getDatabaseConfigurationParameters() {
        ServletContext sc = ActionContext.getContext().
                            getServletContext();
        driverClass = sc.getInitParameter("driverClass");
        dbUrl = sc.getInitParameter("dbUrl");
        user = sc.getInitParameter("user");
        password = sc.getInitParameter("password");
    }

    protected String doExecute() throws java.lang.Exception {
        getDatabaseConfigurationParameters();
        scheduleDb = new ScheduleDb(driverClass, dbUrl, user,
                                    password);
        scheduleDb.populate();
        scheduleItems = scheduleDb.getList();
        columns = scheduleDb.getColumns();
        eventTypes = scheduleDb.getEventTypes();
        return SUCCESS;
    }

    public List getScheduleItems() {
        return scheduleItems;
    }

    public List getColumns() {
        String[] uiColumns = new String[columns.length - 1];
        System.arraycopy(columns, 1, uiColumns, 0,
                         columns.length - 1);
        return Arrays.asList(uiColumns);
    }
}