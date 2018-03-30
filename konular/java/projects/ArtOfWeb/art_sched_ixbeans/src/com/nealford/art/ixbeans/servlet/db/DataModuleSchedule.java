package com.nealford.art.ixbeans.servlet.db;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.borland.dx.dataset.CalcFieldsListener;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.DataModule;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.Locate;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;
import com.borland.jb.util.TriStateProperty;
import com.nealford.art.ixbeans.servlet.entity.ScheduleItemBizRules;

public class DataModuleSchedule implements DataModule {
    ResourceBundle sqlRes = ResourceBundle.getBundle(
            "com.nealford.art.ixbeans.servlet.db.SqlRes");
    static private DataModuleSchedule myDM;
    private Database dbSchedule = new Database();
    private QueryDataSet qryEvents = new QueryDataSet();
    private QueryDataSet qryEventType = new QueryDataSet();
    private Column column2 = new Column();
    private Column column3 = new Column();
    private Column column4 = new Column();
    private java.util.List errorList;
    private Column column5 = new Column();

    public DataModuleSchedule() {
        try {
            jbInit();
            errorList = new ArrayList(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        column5.setColumnName("description");
        column5.setDataType(com.borland.dx.dataset.Variant.STRING);
        column5.setPrecision(50);
        column5.setEditable(true);
        column5.setTableName("event");
        column5.setServerColumnName("description");
        column5.setSqlType(12);
        column5.addColumnChangeListener(new  ColumnChangeAdapter() {
            public void validate(DataSet dataSet, Column column,
                    Variant value) throws Exception,
                    DataSetException {
                column5_validate(dataSet, column, value);
            }
        });
        column4.setColumnName("duration");
        column4.setDataType(com.borland.dx.dataset.Variant.INT);
        column4.setRequired(true);
        column4.setTableName("event");
        column4.setServerColumnName("duration");
        column4.setSqlType(4);
        column4.addColumnChangeListener(new ColumnChangeAdapter() {
            public void validate(DataSet dataSet, Column column,
                                 Variant value) throws Exception,
                    DataSetException {
                column4_validate(dataSet, column, value);
            }
        });
        column3.setColumnName("event_type");
        column3.setDataType(com.borland.dx.dataset.Variant.INT);
        column3.setTableName("event");
        column3.setVisible(TriStateProperty.FALSE);
        column3.setServerColumnName("event_type");
        column3.setSqlType(4);
        column2.setCalcType(com.borland.dx.dataset.CalcType.CALC);
        column2.setCaption("Event Type");
        column2.setColumnName("Event Type");
        column2.setDataType(com.borland.dx.dataset.Variant.STRING);
        column2.setPreferredOrdinal(2);
        column2.setServerColumnName("NewColumn1");
        column2.setSqlType(0);
        qryEventType.setQuery(new QueryDescriptor(dbSchedule,
                sqlRes.getString("event_types"), null, true,
                Load.ALL));
        qryEvents.setQuery(new QueryDescriptor(dbSchedule,
                "select * from event", null, true, Load.ALL));
        qryEvents.addCalcFieldsListener(new CalcFieldsListener() {
            public void calcFields(ReadRow changedRow,
                    DataRow calcRow, boolean isPosted) {
                qryEvents_calcFields(changedRow, calcRow, isPosted);
            }
        });
        dbSchedule.setConnection(new ConnectionDescriptor(
                "jdbc:mysql://localhost/schedule", "root",
                "marathon", false, "com.mysql.jdbc.Driver"));
        qryEvents.setColumns(new Column[] {column2, column4,
                             column5, column3});
    }

    public static DataModuleSchedule getDataModule() {
        if (myDM == null) {
            myDM = new DataModuleSchedule();
        }
        return myDM;
    }

    public Database getDbSchedule() {
        return dbSchedule;
    }

    public QueryDataSet getQryEvents() {
        return qryEvents;
    }

    public QueryDataSet getQryEventType() {
        return qryEventType;
    }

    void qryEvents_calcFields(ReadRow changedRow, DataRow calcRow,
                              boolean isPosted) {
        if (!qryEventType.isOpen())
            qryEventType.open();
        if (!qryEvents.isOpen())
            qryEvents.open();
        DataRow locateRow = new DataRow(qryEventType,
                                        "event_type_key");
        locateRow.setInt("event_type_key",
                         changedRow.getInt("event_type"));
        qryEventType.locate(locateRow, Locate.FIRST);
        calcRow.setString("event type",
                          qryEventType.getString("event_text"));
    }

    public java.util.List getErrorList() {
        return errorList;
    }

    void column4_validate(DataSet dataSet, Column column,
                          Variant value)
            throws Exception, DataSetException {
        ScheduleItemBizRules bizRule = ScheduleItemBizRules.getInstance();
        String error = bizRule.validateDuration(value.getAsInt());
        if (error != null)
            errorList.add(error);
    }

    void column5_validate(DataSet dataSet, Column column,
                          Variant value)
            throws Exception, DataSetException {
        ScheduleItemBizRules bizRule = ScheduleItemBizRules.getInstance();
        String error = bizRule.validateText(value.getString());
        if (error != null)
            errorList.add(error);
    }
}