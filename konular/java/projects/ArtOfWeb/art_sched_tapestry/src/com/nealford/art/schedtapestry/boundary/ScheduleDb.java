package com.nealford.art.schedtapestry.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.util.*;
import com.nealford.art.schedtapestry.entity.*;
import com.nealford.art.schedtapestry.util.*;

public class ScheduleDb {
    private List list;
    private Map eventTypes;
    private static final String COLS[] = {"EVENT_KEY", "START",
            "DURATION", "DESCRIPTION", "EVENT_TYPE"};
    private Connection connection;
    private static final String SQL_SELECT = "SELECT * FROM event";
    private static final String SQL_INSERT =
            "INSERT INTO event (start, duration, description, " +
            "event_type) VALUES(?, ?, ?, ?)";
    private static final String SQL_EVENT_TYPES =
            "SELECT event_type_key, event_text FROM event_types";
    private String driverClass;
    private String dbUrl;
    private String user;
    private String password;


    public ScheduleDb(String driverClass, String url, String user, String password) {
        this.driverClass = driverClass;
        this.dbUrl = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() {
        //-- naive, inefficient connection to the database
        //-- to be improved later
        Connection c = null;
        try {
            Class.forName(driverClass);
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", password);
            c = DriverManager.getConnection(dbUrl, info);
        } catch (ClassNotFoundException cnfx) {
            throw new RuntimeException(cnfx.getMessage());
        } catch (SQLException sqlx) {
            throw new RuntimeException(sqlx.getMessage());
        }
        return c;
    }

    public void populate() throws ScheduleException {
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
                int eventTypeKey = rs.getInt(COLS[4]);
                si.setEventType((String) eventTypes.get(new Integer(eventTypeKey)));
                list.add(si);
            }
        } catch (SQLException sqlx) {
            throw new ScheduleException(sqlx);
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
            ScheduleException {
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

            ps.setInt(4, getEventTypeKey(item.getEventType()));
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

    private int getEventTypeKey(String eventType) {
        Map eventTypes = getEventTypes();
        Set entrySet = eventTypes.entrySet();
        Iterator i = entrySet.iterator();
        int foundKey = -1;
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            if (entry.getValue().equals(eventType))
                foundKey = ((Integer) entry.getKey()).intValue();
        }
        return foundKey;

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

    public String[] listEventTypes() {
        Map eventTypes = getEventTypes();
        String[] s = new String[eventTypes.size()];
        Iterator it = eventTypes.values().iterator();
        int i = 0;
        while (it.hasNext())
            s[i++] = (String) it.next();
        return s;
    }

    public List getList() {
        return list;
    }
    public String getDriverClass() {
        return driverClass;
    }
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static String[] getColumns() {
        return COLS;
    }

}