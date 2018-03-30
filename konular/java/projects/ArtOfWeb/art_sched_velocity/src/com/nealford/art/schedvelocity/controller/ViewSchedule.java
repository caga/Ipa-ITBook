package com.nealford.art.schedvelocity.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nealford.art.schedvelocity.boundary.ScheduleDb;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.servlet.VelocityServlet;


public class ViewSchedule extends VelocityServlet {
    private final String BOUNDARY_SESSION_KEY = "scheduleDb";

    public Template handleRequest(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Context context) throws
            ServletException, IOException {

        ScheduleDb scheduleDb = getOrCreateBoundary(request);
        String[] displayColumns = buildDisplayColumns(scheduleDb);
        populateContext(context, scheduleDb, displayColumns);
        Template template = null;
        try {
            template = getTemplate("ViewSchedule.vm");
        } catch (ParseErrorException pex) {
            log("ViewSchedule: ", pex);
        } catch (ResourceNotFoundException rnfx) {
            log("ViewSchedule: ", rnfx);
        } catch (Exception x) {
            log("ViewSchedule: ", x);
        }
        return template;
    }

    private void populateContext(Context context,
                                 ScheduleDb scheduleDb,
                                 String[] displayColumns) {
        context.put("columnHeaders", displayColumns);
        context.put("scheduleList", scheduleDb.getList());
    }

    private ScheduleDb getOrCreateBoundary(HttpServletRequest
            request) {
        HttpSession session = request.getSession(true);
        ScheduleDb scheduleDb = (ScheduleDb) session.getAttribute(
                BOUNDARY_SESSION_KEY);
        if (scheduleDb == null) {
            scheduleDb = new ScheduleDb();
            session.setAttribute(BOUNDARY_SESSION_KEY, scheduleDb);
        }

        try {
            scheduleDb.populate();
        } catch (Exception x) {
            log("Error: ScheduleBean.populate()", x);
        }
        return scheduleDb;
    }

    private String[] buildDisplayColumns(ScheduleDb scheduleBean) {
        int numOfDisplayColumns = scheduleBean.
                getDisplayColumnHeaders().length -1;
        String[] displayColumns = new String[numOfDisplayColumns];
        System.arraycopy(scheduleBean.getDisplayColumnHeaders(),
                         1, displayColumns, 0, numOfDisplayColumns);
        return displayColumns;
    }
}