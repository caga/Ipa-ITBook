package com.nealford.art.ixbeans.servlet.db;

import com.borland.dx.dataset.DataModule;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.Database;

public class DataModuleSched implements DataModule {
    static private DataModuleSched myDM;
    private Database dtbsSchedule = new Database();

    public DataModuleSched() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        dtbsSchedule.setConnection(new ConnectionDescriptor(
                "jdbc:mysql://localhost/schedule", "root",
                "marathon", false, "com.mysql.jdbc.Driver"));
    }

    public static DataModuleSched getDataModule() {
        if (myDM == null) {
            myDM = new DataModuleSched();
        }
        return myDM;
    }

    public com.borland.dx.sql.dataset.Database getDtbsSchedule() {
        return dtbsSchedule;
    }
}