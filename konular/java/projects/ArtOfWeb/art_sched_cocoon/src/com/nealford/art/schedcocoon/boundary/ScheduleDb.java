package com.nealford.art.schedcocoon.boundary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nealford.art.schedcocoon.entity.ScheduleItem;
import com.nealford.art.schedcocoon.util.ScheduleAddException;

public class ScheduleDb {
    private List list;
    private Map eventTypes;
    private static final String COLS[] = {"EVENT_KEY", "START",
            "DURATION", "DESCRIPTION", "EVENT_TYPE"};
    private static final String DISPLAY_COLS[] = {"Id", "Start",
            "Duration", "Description", "Event Type"};
    private Connection connection;
    private static final String DB_CLASS =
            "org.gjt.mm.mysql.Driver";
    private static final String DB_URL =
            "jdbc:mysql://localhost/schedule?user=root&password=marathon";
    private static final String SQL_SELECT =
            "SELECT * FROM event";
    private static final String SQL_INSERT =
            "INSERT INTO event (start, duration, description, " +
            "event_type) VALUES(?, ?, ?, ?)";
    private static final String SQL_EVENT_TYPES =
            "SELECT event_type_key, event_text FROM event_types";


    private Connection getConnection() {
        //-- naive, inefficient connection to the database
        //-- to be improved in subsequent chapter
        Connection c = null;
        try {
            Class.forName(DB_CLASS);
            c = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException cnfx) {
            throw new RuntimeException(cnfx.getMessage());
        } catch (SQLException sqlx) {
            throw new RuntimeException(sqlx.getMessage());
        }
        return c;
    }

    public String[] getColumnHeaders() {
        return COLS;
    }

    public String[] getDisplayColumnHeaders() {
        return DISPLAY_COLS;
    }

    public void populate() throws SQLException {
        //-- connection to database
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        list = new ArrayList(10);
        Map eventTypes = getEventTypes();
        try {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(SQL_SELECT);
            int i = 0;
            //-- build list of items
            while (rs.next()) {
                ScheduleItem si = new ScheduleItem();
                si.setStart(rs.getString(COLS[1]));
                si.setDuration(rs.getInt(COLS[2]));
                si.setText(rs.getString(COLS[3]));
                si.setEventTypeKey(rs.getInt(COLS[4]));
                si.setEventType((String) eventTypes.get(
                        new Integer(si.getEventTypeKey())));
                list.add(si);
            }
        } finally {
            try {
                rs.close();
                s.close();
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void addRecord(ScheduleItem item) throws
            ScheduleAddException {
        Connection con = null;
        PreparedStatement ps = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, item.getStart());
            ps.setInt(2, item.getDuration());
            ps.setString(3, item.getText());
            ps.setInt(4, item.getEventTypeKey());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new ScheduleAddException("Insert failed");
            }
            populate();
        } catch (SQLException sqlx) {
            throw new ScheduleAddException(sqlx.getMessage());
        } finally {
            try {
                rs.close();
                s.close();
                con.close();
            } catch (Exception ignored) {
            }
        }
    }

    public Map getEventTypes() {
        if (eventTypes == null) {
            Connection con = null;
            Statement s = null;
            ResultSet rs = null;
            try {
                con = getConnection();
                s = con.createStatement();
                rs = s.executeQuery(SQL_EVENT_TYPES);
                eventTypes = new HashMap();
                while (rs.next())
                    eventTypes.put(rs.getObject("event_type_key"),
                                   rs.getString("event_text"));
            } catch (SQLException sqlx) {
                throw new RuntimeException(sqlx.getMessage());
            } finally {
                try {
                    rs.close();
                    s.close();
                    con.close();
                } catch (Exception ignored) {
                }
            }
        }
        return eventTypes;
    }

    public List getList() {
        return list;
    }

}