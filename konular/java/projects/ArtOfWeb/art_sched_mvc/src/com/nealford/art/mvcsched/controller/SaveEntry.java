package com.nealford.art.mvcsched.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nealford.art.mvcsched.boundary.ScheduleDb;
import com.nealford.art.mvcsched.entity.ScheduleItem;
import com.nealford.art.mvcsched.util.ScheduleAddException;

public class SaveEntry extends HttpServlet {


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {
        ScheduleItem newItem = populateNewItemFromRequest(request);
        List validationErrors = newItem.validate();
        if (!validationErrors.isEmpty())
            returnToInput(request, response, newItem,
                          validationErrors);
        else {
            addNewItem(newItem);
            forwardToSchedule(request, response);
        }
    }

    private void addNewItem(ScheduleItem newItem) throws
            ServletException, IOException {
        try {
            new ScheduleDb().addRecord(newItem);
        } catch (ScheduleAddException sax) {
            getServletContext().log("Add error", sax);
        }
    }

    private void forwardToSchedule(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/viewschedule");
        dispatcher.forward(request, response);
    }

    private void returnToInput(HttpServletRequest request,
                               HttpServletResponse response,
                               ScheduleItem newItem,
                               List validationErrors) throws
            ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                "/ScheduleEntryView.jsp");
        request.setAttribute("scheduleItem", newItem);
        request.setAttribute("errors", validationErrors);
        dispatcher.forward(request, response);
        return;
    }

    private ScheduleItem populateNewItemFromRequest(
            HttpServletRequest
            request) {
        ScheduleItem newItem = new ScheduleItem();
        populateDuration(request, newItem);
        populdateEventTypeKey(request, newItem);
        populateStart(request, newItem);
        populateText(request, newItem);
        return newItem;
    }

    private void populateText(HttpServletRequest request,
                              ScheduleItem newItem) {
        String text = request.getParameter("text");
        if (text != null)
            newItem.setText(text);
    }

    private void populateStart(HttpServletRequest request,
                               ScheduleItem newItem) {
        String start = request.getParameter("start");
        if (start != null)
            newItem.setStart(start);
    }

    private void populdateEventTypeKey(HttpServletRequest request,
                                       ScheduleItem newItem) {
        String typeKey = request.getParameter("eventTypeKey");
        try {
            if (typeKey != null)
                newItem.setEventTypeKey(Integer.parseInt(typeKey));
        } catch (NumberFormatException nfx) {
            getServletContext().log("Conversion error:eventTypeKey",
                                    nfx);
        }
    }

    private void populateDuration(HttpServletRequest
                                  request, ScheduleItem newItem) {
        String duration = request.getParameter("duration");
        try {
            if (duration != null)
                newItem.setDuration(Integer.parseInt(duration));
        } catch (NumberFormatException nfx) {
            getServletContext().log("Conversion error:duration",
                                    nfx);
        }
    }
}