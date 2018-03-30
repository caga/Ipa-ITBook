package com.nealford.art.schedtapestry.util;

import java.io.IOException;
import net.sf.tapestry.ApplicationServlet;
import net.sf.tapestry.RequestContext;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Welcome extends ApplicationServlet {
    private static Logger logger = Logger.getLogger(Welcome.class);

    protected String getApplicationSpecificationPath() {
        return "/resources/sched-tapestry.application";
    }

    protected void setupLogging()
            throws javax.servlet.ServletException {
        super.setupLogging();
        logger.getRootLogger().addAppender(
                new ConsoleAppender(new SimpleLayout()));
        String logFileLocation = getServletContext().
                getInitParameter("log-file-location");
        try {
            logger.getRootLogger().addAppender(
                    new FileAppender(new SimpleLayout(),
                                     logFileLocation));
        } catch (IOException ex) {
            logger.error(ex);
        }
        logger.setLevel(Level.INFO);
    }
}