package com.nealford.art.schedvelocity.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nealford.art.schedvelocity.boundary.ScheduleDb;
import com.nealford.art.schedvelocity.entity.ScheduleItem;
import com.nealford.art.schedvelocity.util.ScheduleAddException;
import com.nealford.art.schedvelocity.util.ScheduleSaveBase;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class SaveEntry extends ScheduleSaveBase {

    public Template handleRequest(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Context context) throws
            ServletException, IOException {
        ScheduleItem newItem = populateItemFromRequest(request);
        Template template = null;
        try {
            ScheduleDb scheduleDb = getBoundary(
                    request.getSession(false));
            List validationErrors = newItem.validate();
            if (!validationErrors.isEmpty()) {
                populateContext(context, newItem, scheduleDb,
                                 validationErrors);
                template = getTemplate("ScheduleEntryView.vm");
            } else {
                scheduleDb.addRecord(newItem);
                RequestDispatcher dispatcher = request.
                        getRequestDispatcher(
                        "/viewschedule");
                dispatcher.forward(request, response);
            }
        } catch (ScheduleAddException sax) {
            log("Add error", sax);
        } catch (ParseErrorException pex) {
            log("SaveEntry: ", pex);
        } catch (ResourceNotFoundException rnfx) {
            log("SaveEntry: ", rnfx);
        } catch (Exception x) {
            log("SaveEntry: ", x);
        }
        return template;
    }

    private void populateContext(Context context,
                                  ScheduleItem newItem,
                                  ScheduleDb scheduleDb,
                                  List validationErrors) {
        context.put("scheduleItem", newItem);
        context.put("errors", validationErrors);
        context.put("eventTypes", scheduleDb.getEventTypes());
    }

    private ScheduleItem populateItemFromRequest(HttpServletRequest
            request) {
        ScheduleItem newItem = new ScheduleItem();
        String duration = request.getParameter("duration");
        try {
            if (duration != null)
                newItem.setDuration(Integer.parseInt(duration));
        } catch (NumberFormatException nfx) {
            log("Conversion error:duration", nfx);
        }
        String typeKey = request.getParameter("eventTypeKey");
        try {
            if (typeKey != null)
                newItem.setEventTypeKey(Integer.parseInt(typeKey));
        } catch (NumberFormatException nfx) {
            log("Conversion error:eventTypeKey", nfx);
        }
        String start = request.getParameter("start");
        if (start != null)
            newItem.setStart(start);
        String text = request.getParameter("text");
        if (text != null)
            newItem.setText(text);
        return newItem;
    }
}