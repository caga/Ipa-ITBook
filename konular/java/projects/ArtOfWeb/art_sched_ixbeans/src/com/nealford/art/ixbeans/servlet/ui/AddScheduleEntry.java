package com.nealford.art.ixbeans.servlet.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.Locate;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.internetbeans.IxComboBox;
import com.borland.internetbeans.IxPageProducer;
import com.borland.internetbeans.IxPushButton;
import com.borland.internetbeans.IxSubmitButton;
import com.borland.internetbeans.IxTextField;
import com.borland.internetbeans.SubmitEvent;
import com.nealford.art.ixbeans.servlet.db.DataModuleSchedule;

public class AddScheduleEntry extends HttpServlet {
    private IxPageProducer ixPageProducer1 = new IxPageProducer();
    private IxSubmitButton ixSubmitButton1 = new IxSubmitButton();
    private IxTextField ixtxtDuration = new IxTextField();
    private IxTextField ixtxtStart = new IxTextField();
    private IxTextField ixtxtText = new IxTextField();
    private IxComboBox ixcbEventType = new IxComboBox();
    private DataModuleSchedule dataModuleSchedule;
    private QueryDataSet referenceForInsert;
    private IxPushButton ixPushButton1 = new IxPushButton();

    public AddScheduleEntry() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        ixcbEventType.setOptions(getEventTypeList(request));
        ixPageProducer1.servletGet(this, request, response);
    }

    private List getEventTypeList(HttpServletRequest request) {
        DataModuleSchedule dm = (DataModuleSchedule)
                ixPageProducer1.getSessionDataModule(
                request.getSession());
        dm.getQryEventType().open();
        dm.getQryEventType().first();
        List items = new ArrayList(10);
        while (dm.getQryEventType().inBounds()) {
            items.add(dm.getQryEventType().getString("event_text"));
            dm.getQryEventType().next();
        }
        return items;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {

        DataModuleSchedule dm = (DataModuleSchedule)
                                ixPageProducer1.
                                getSessionDataModule(
                request.getSession());
        List errorList = (List) request.getAttribute("errorList");
        if (errorList != null)
            outputErrors(request, response, errorList);
        else {
            dm.getQryEvents().insertRow(false);
            ixPageProducer1.servletPost(this, request, response);
        }
    }

    private void outputErrors(HttpServletRequest request,
                              HttpServletResponse response,
                              List errorList) throws
            IOException {
        PrintWriter out = response.getWriter();
        Iterator err = errorList.iterator();
        out.println("<font color='red'>");
        while (err.hasNext())
            out.println(err.next() + "<br>");
        out.println("</font>");
        errorList.clear();
        ixPageProducer1.servletGet(this, request, response);
    }

    private void jbInit() throws Exception {
        dataModuleSchedule = com.nealford.art.ixbeans.servlet.db.
                             DataModuleSchedule.getDataModule();
        ixcbEventType.setColumnName("Event Type");
        ixcbEventType.setDataSet(dataModuleSchedule.getQryEvents());
        ixcbEventType.setControlName("eventTypeKey");
        ixtxtStart.setColumnName("start");
        ixtxtStart.setDataSet(dataModuleSchedule.getQryEvents());
        ixtxtStart.setControlName("start");
        ixtxtText.setColumnName("description");
        ixtxtText.setDataSet(dataModuleSchedule.getQryEvents());
        ixtxtText.setControlName("text");
        ixSubmitButton1.setControlName("submit");
        ixSubmitButton1.setElementId("submit");
        ixSubmitButton1.addSubmitListener(new com.borland.
                internetbeans.SubmitListener() {
            public void submitPerformed(SubmitEvent e) {
                ixSubmitButton1_submitPerformed(e);
            }
        });
        ixtxtDuration.setColumnName("duration");
        ixtxtDuration.setDataSet(dataModuleSchedule.getQryEvents());
        ixPushButton1.setPageProducer(ixPageProducer1);
        ixSubmitButton1.setPageProducer(ixPageProducer1);
        ixcbEventType.setPageProducer(ixPageProducer1);
        ixtxtText.setPageProducer(ixPageProducer1);
        ixtxtStart.setPageProducer(ixPageProducer1);
        ixtxtDuration.setControlName("duration");
        ixtxtDuration.setPageProducer(ixPageProducer1);
        ixPageProducer1.setHtmlFile("/home/dev/art/art_sched_ixbeans/root/AddScheduleEntry.html");
        ixPageProducer1.setRootPath(
                "/home/dev/art/art_sched_ixbeans/root/");
        ixPageProducer1.setDataModule(dataModuleSchedule);
    }

    void ixSubmitButton1_submitPerformed(SubmitEvent e) {
        DataModuleSchedule dm = (DataModuleSchedule)
                ixPageProducer1.getSessionDataModule(
                e.getSession());
        lookupEventKeyAndUpdateRowValue(e, dm);
        RequestDispatcher rd = null;
        List errors = dm.getErrorList();
        if (errors.size() > 0) {
            forwardToErrorView(e, rd, errors);
            return;
        }
        saveChangesToDatabase(dm);
        dm.getErrorList().clear();
        rd = e.getRequest().getRequestDispatcher(
                "/viewschedule");
        try {
            rd.forward(e.getRequest(), e.getResponse());
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void saveChangesToDatabase(DataModuleSchedule dm) {
        dm.getQryEvents().post();
        dm.getQryEvents().saveChanges();
    }

    private void lookupEventKeyAndUpdateRowValue(SubmitEvent e,
                                DataModuleSchedule dm) {
        DataRow lookupRow = new DataRow(dm.getQryEventType(),
                                        "event_text");
        DataRow resultRow = new DataRow(dm.getQryEventType());
        lookupRow.setString("event_text",
                            e.getRequest().
                            getParameter("eventTypeKey"));
        if (dm.getQryEventType().lookup(lookupRow, resultRow,
                                        Locate.FIRST))
            dm.getQryEvents().setInt("event_type",
                    resultRow.getInt("event_type_key"));
    }

    private void forwardToErrorView(SubmitEvent e,
                                    RequestDispatcher rd,
                                    List errors) {
        e.getRequest().setAttribute("errorList", errors);
        rd = e.getRequest().getRequestDispatcher(
                "/addscheduleentry");
        try {
            rd.forward(e.getRequest(), e.getResponse());

        } catch (ServletException sx) {
            e.getSession().getServletContext().log(
                    "Formard error from submit", sx);
        } catch (IOException ix) {
            e.getSession().getServletContext().log(
                    "Formard error from submit", ix);
        }
    }
}