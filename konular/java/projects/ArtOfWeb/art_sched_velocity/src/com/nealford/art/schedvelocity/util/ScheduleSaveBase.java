package com.nealford.art.schedvelocity.util;

import javax.servlet.http.HttpSession;
import com.nealford.art.schedvelocity.boundary.ScheduleDb;
import org.apache.velocity.servlet.VelocityServlet;

public abstract class ScheduleSaveBase extends VelocityServlet {
    protected final String BOUNDARY_SESSION_KEY = "scheduleDb";

    protected ScheduleDb getBoundary(HttpSession session) {
        if (session == null)
            return null;
        return (ScheduleDb) session.getAttribute(
                BOUNDARY_SESSION_KEY);
    }
}