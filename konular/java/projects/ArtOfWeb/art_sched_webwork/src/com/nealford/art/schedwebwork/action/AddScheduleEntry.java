package com.nealford.art.schedwebwork.action;

import java.io.IOException;
import java.util.Map;

import com.nealford.art.schedwebwork.boundary.ScheduleDb;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class AddScheduleEntry extends AddScheduleBase {
    private static final Logger logger = Logger.getLogger(
            AddScheduleEntry.class);

    static {
        try {
            logger.addAppender(new FileAppender(new SimpleLayout(),
                    "c:/temp/sched-webwork.log"));
        } catch (IOException ex) {
            logger.error("Can't create log file");
        }
    }

    protected String doExecute() throws java.lang.Exception {
        Map errors = scheduleItem.validate();
        if (!errors.isEmpty())
            return ERROR;
        ScheduleDb scheduleDb = getScheduleBoundary();
        scheduleDb.addRecord(scheduleItem);
        return SUCCESS;
    }

}