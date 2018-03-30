package com.nealford.art.ejbsched.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.ejbsched.model.*;
import javax.naming.Context;

public class SaveEntry extends HttpServlet {
    private Context context;

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        context = getContext(request);
        ScheduleItem newItem = initializeItemFrom(request);
        List errors = validateNewItem(newItem);
        if (errors.isEmpty()) {
            addRecord(newItem);
            fowardToDefaultView(request, response);
        } else
            forwardToValidationError(request, response, newItem,
                                     errors);
    }

    private ScheduleItem initializeItemFrom(
            HttpServletRequest request) {
        ScheduleItem newItem = new ScheduleItem();
        String duration = request.getParameter("duration");
        try {
            if (duration != null)
                newItem.setDuration(Integer.parseInt(duration));
        } catch (NumberFormatException nfx) {
            getServletContext().log("Conversion error:duration",
                    nfx);
        }

        String typeKey = request.getParameter("eventTypeKey");
        try {
            if (typeKey != null)
                newItem.setEventTypeKey(Integer.parseInt(typeKey));
        } catch (NumberFormatException nfx) {
            getServletContext().log("Conversion error:eventTypeKey",
                    nfx);
        }

        String start = request.getParameter("start");
        if (start != null)
            newItem.setStart(start);

        String text = request.getParameter("text");
        if (text != null)
            newItem.setText(text);
        return newItem;
    }

    private void fowardToDefaultView(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/ViewSchedule");
        dispatcher.forward(request, response);
    }

    private void addRecord(ScheduleItem newItem) {
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setContext(context);
        try {
            scheduleBean.addRecord(newItem);
        } catch (ScheduleAddException sax) {
            getServletContext().log("Add error", sax);
        }
    }

    private List validateNewItem(ScheduleItem newItem)
            throws ServletException, IOException {

        newItem.setContext(context);
        return newItem.validate();
    }

    private void forwardToValidationError(
            HttpServletRequest request,
            HttpServletResponse response, ScheduleItem newItem,
            List validationErrors)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                "/ScheduleEntryView.jsp");
        request.setAttribute("scheduleItem", newItem);
        request.setAttribute("errors", validationErrors);
        dispatcher.forward(request, response);
    }

    private Context getContext(HttpServletRequest request)
            throws ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            getServletContext().log("SaveEntry: no user session");
            throw new ServletException("No user session");
        }
        return (Context) session.getAttribute("context");
    }

}