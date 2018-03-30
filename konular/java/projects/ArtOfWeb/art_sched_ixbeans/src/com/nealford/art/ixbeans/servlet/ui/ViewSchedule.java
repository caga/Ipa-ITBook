package com.nealford.art.ixbeans.servlet.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.borland.internetbeans.IxLink;
import com.borland.internetbeans.IxPageProducer;
import com.borland.internetbeans.IxTable;
import com.nealford.art.ixbeans.servlet.db.DataModuleSchedule;

public class ViewSchedule extends HttpServlet {
    IxPageProducer ixPageProducer1 = new IxPageProducer();
    IxTable ixTable1 = new IxTable();
    DataModuleSchedule dataModuleSchedule;
    IxLink ixLink1 = new IxLink();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        ixPageProducer1.servletGet(this, request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {
        DataModuleSchedule dm =
                (DataModuleSchedule) ixPageProducer1.
                getSessionDataModule(request.getSession());
        dm.getQryEvents().refresh();
        ixPageProducer1.servletGet(this, request, response);
    }

    public ViewSchedule() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        dataModuleSchedule = com.nealford.art.ixbeans.servlet.db.
                             DataModuleSchedule.getDataModule();
        ixTable1.setDataSet(dataModuleSchedule.getQryEvents());
        ixTable1.setRowCount(100);
        ixPageProducer1.setDataModule(dataModuleSchedule);
        ixLink1.setPageProducer(ixPageProducer1);
        ixTable1.setPageProducer(ixPageProducer1);
        ixPageProducer1.setHtmlFile("/home/dev/art/art_sched_ixbeans/root/sched-template.html");
        ixPageProducer1.setRootPath("/home/dev/art/art_sched_ixbeans/root/");
        ixTable1.setElementId("scheduletable");
        ixLink1.setElementId("addlink");
        ixLink1.setLink("/sched-ixbeans/addscheduleentry");
    }
}