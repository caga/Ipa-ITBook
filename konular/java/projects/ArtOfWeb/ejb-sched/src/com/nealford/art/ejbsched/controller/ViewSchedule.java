package com.nealford.art.ejbsched.controller;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.nealford.art.ejbsched.model.ScheduleBean;

public class ViewSchedule extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        Context c = establishContext(request);
        forwardToView(request, response, populateModel(c));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

    private void forwardToView(HttpServletRequest request,
                               HttpServletResponse response,
                               ScheduleBean scheduleBean) throws
            ServletException, IOException {
        request.setAttribute("scheduleBean", scheduleBean);
        RequestDispatcher rd = request.getRequestDispatcher(
                "/ScheduleView.jsp");
        rd.forward(request, response);
    }

    private ScheduleBean populateModel(Context c) {
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setContext(c);
        try {
            scheduleBean.populate();
        } catch (Exception x) {
            getServletContext().log(
                    "Error: ScheduleBean.populate()");
        }
        return scheduleBean;
    }

    private Context establishContext(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Context c = (Context) session.getAttribute("context");
        if (c == null) {
            c = getInitialContext();
            session.setAttribute("context", getInitialContext());
        }
        return c;
    }

    private Context getInitialContext() {
        Context c = null;
        try {
            c = new InitialContext();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return c;
    }

}