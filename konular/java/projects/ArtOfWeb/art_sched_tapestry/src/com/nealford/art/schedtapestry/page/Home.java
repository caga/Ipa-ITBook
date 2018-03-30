package com.nealford.art.schedtapestry.page;

import com.nealford.art.schedtapestry.boundary.ScheduleDb;
import com.nealford.art.schedtapestry.util.ScheduleException;
import net.sf.tapestry.IEngine;
import net.sf.tapestry.html.BasePage;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Home extends BasePage {
    static Logger logger = Logger.getLogger(Home.class);
    private ScheduleDb scheduleDb;

    static {
        logger.addAppender(new ConsoleAppender(new SimpleLayout()));
        logger.setLevel(Level.DEBUG);
    }

    public Home() {
        logger.debug("Entering Home page constructor");
    }

    public ScheduleDb getScheduleDb() {
        if (scheduleDb == null)
            scheduleDb = createScheduleDb(getEngine());
        try {
            scheduleDb.populate();
        } catch (ScheduleException ex) {
            logger.error("Home.getScheduleDb", ex);
        }
        return scheduleDb;
    }

    private ScheduleDb createScheduleDb(IEngine engine) {
        String dbUrl = engine.getSpecification().
                       getProperty(
                "dbUrl");
        String driverClass = engine.getSpecification().
                             getProperty("driverClass");
        String user = engine.getSpecification().
                      getProperty(
                "user");
        String password = engine.getSpecification().
                          getProperty(
                "password");
        return new ScheduleDb(driverClass, dbUrl, user,
                                    password);
    }

    protected void firePageBeginRender() {
        super.firePageBeginRender();
        try {
            scheduleDb.populate();
        } catch (ScheduleException ex) {
            logger.error("Home.beginResponse()", ex);
        }

    }
}