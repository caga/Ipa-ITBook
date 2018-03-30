package com.nealford.art.mvcsched.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import com.nealford.art.mvcsched.boundary.ScheduleDb;
import javax.servlet.http.*;

public class ViewSchedule extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        ScheduleDb scheduleBean = new ScheduleDb();
        try {
            scheduleBean.populate();
        } catch (Exception x) {
            getServletContext().log(
                "Error: ScheduleBean.populate()", x);
        }
        request.setAttribute("scheduleItems",
                             scheduleBean.getList());
        RequestDispatcher rd = request.getRequestDispatcher(
                "/ScheduleView.jsp");
        rd.forward(request, response);
    }
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws javax.servlet.ServletException,
            java.io.IOException {
        doGet(req, resp);
    }
}