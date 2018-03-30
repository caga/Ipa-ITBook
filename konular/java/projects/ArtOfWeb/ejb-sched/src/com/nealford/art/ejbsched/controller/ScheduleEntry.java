package com.nealford.art.ejbsched.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.nealford.art.ejbsched.model.ScheduleBean;
import javax.naming.Context;

public class ScheduleEntry extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        ScheduleBean scheduleBean = createModel(request);
        forwardToView(request, response, scheduleBean);
    }

    private void forwardToView(HttpServletRequest request,
                               HttpServletResponse response,
                               ScheduleBean scheduleBean)
            throws IOException, ServletException {
        getSession(request).setAttribute("scheduleBean", scheduleBean);
        request.getRequestDispatcher("/ScheduleEntryView.jsp").
                forward(request, response);
    }

    private ScheduleBean createModel(HttpServletRequest request) {
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setContext(getContext(request));
        return scheduleBean;
    }

    private Context getContext(HttpServletRequest request) {
        return (Context) getSession(request).getAttribute("context");
    }

    private HttpSession getSession(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null)
            getServletContext().log("ScheduleEntry: no session");
        return s;
    }
}