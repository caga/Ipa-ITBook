package com.nealford.art.schedwebwork.action;

import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class AddScheduleItem extends AddScheduleBase {
    private static final Logger logger = Logger.getLogger(
            AddScheduleItem.class);

    static {
        try {
            logger.addAppender(new FileAppender(new SimpleLayout(),
                    "c:/temp/sched-webwork.log"));
        } catch (IOException ex) {
            logger.error("Can't create log file");
        }
    }

    protected String doExecute() throws java.lang.Exception {
        return SUCCESS;
    }
}