package com.nealford.art.schedstruts.boundary;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import com.nealford.art.schedstruts.entity.ScheduleItem;
import com.nealford.art.schedstruts.util.ScheduleAddException;
import org.apache.struts.util.LabelValueBean;

public class ScheduleDb {
    private List events;
    private Map eventTypes;
    private List eventTypeLabels;
    private DataSource dataSource;
    //-- database related constants
    private static final String COLS[] = {"EVENT_KEY", "START",
            "DURATION", "DESCRIPTION", "EVENT_TYPE"};
    private static final String SQL_SELECT = "SELECT * FROM event";
    private static final String SQL_INSERT =
            "INSERT INTO event (start, duration, description, " +
            "event_type) VALUES(?, ?, ?, ?)";
    private static final String SQL_EVENT_TYPES =
            "SELECT event_type_key, event_text FROM event_types";
    //-- exception constants
    private static final String ERR_NO_CONNECTION =
            "Internal error: Connection property not set";
    private static final String ERR_INSERT_FAILED = "Insert failed";

    public void populate() throws SQLException {
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        events = new ArrayList(10);
        Map eventTypes = getEventTypes();
        try {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(SQL_SELECT);
            //-- build list of items
            while (rs.next()) {
                ScheduleItem si = new ScheduleItem();
                si.setStart(rs.getString(COLS[1]));
                si.setDuration(rs.getInt(COLS[2]));
                si.setText(rs.getString(COLS[3]));
                si.setEventTypeKey(rs.getInt(COLS[4]));
                si.setEventType((String) eventTypes.get(
                        new Integer(si.getEventTypeKey())));
                events.add(si);
            }
        } finally {
            try {
                rs.close();
                s.close();
                con.close();
            } catch (SQLException ignored) {}
        }
    }

    public void addRecord(ScheduleItem item) throws
            ScheduleAddException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, item.getStart());
            ps.setInt(2, item.getDuration());
            ps.setString(3, item.getText());
            ps.setInt(4, item.getEventTypeKey());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
                throw new ScheduleAddException(ERR_INSERT_FAILED);
            //-- repopulate the list to reflect insertion
            populate();
        } catch (SQLException sqlx) {
            throw new ScheduleAddException(sqlx.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception ignored) {}
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
                    eventTypes.put(
                        new Integer(rs.getInt("event_type_key")),
                        rs.getString("event_text"));
            } catch (SQLException sqlx) {
                throw new RuntimeException(sqlx.getMessage());
            } finally {
                try {
                    rs.close();
                    s.close();
                    con.close();
                } catch (Exception ignored) {}
            }
        }
        return eventTypes;
    }

    public List getEventTypeLabels() {
        if (eventTypeLabels == null) {
            Map eventTypes = getEventTypes();
            eventTypeLabels = new ArrayList(5);
            Iterator ei = eventTypes.keySet().iterator();
            while (ei.hasNext()) {
                Integer key = (Integer) ei.next();
                String value = (String) eventTypes.get(key);
                LabelValueBean lvb = new LabelValueBean(value,
                        key.toString());
                eventTypeLabels.add(lvb);
            }
        }
        return eventTypeLabels;
    }


    public List getList() {
        return events;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private Connection getConnection()
            throws SQLException, RuntimeException {
        Connection con = dataSource.getConnection();
        if (con == null)
            throw new RuntimeException(ERR_NO_CONNECTION);
        return con;
    }
}