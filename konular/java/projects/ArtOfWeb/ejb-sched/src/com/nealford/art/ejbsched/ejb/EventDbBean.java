package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import com.nealford.art.ejbsched.model.ScheduleItem;
import javax.rmi.PortableRemoteObject;

public class EventDbBean implements SessionBean {
    private SessionContext sessionContext;
    private static final String SQL_SELECT = "SELECT * FROM event";
    private static final String COLS[] = {"EVENT_KEY", "START",
            "DURATION", "DESCRIPTION", "EVENT_TYPE"};

    public void ejbCreate() {
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    private DataSource getDataSource() throws RemoteException {
        DataSource ds = null;
        try {
            Context c = new InitialContext();
            Object o = c.lookup("java:/MySQLDS");
            ds = (DataSource) PortableRemoteObject.narrow(o,
                    DataSource.class);
        }
        catch (ClassCastException ex) {
            throw new RemoteException(ex.getMessage());
        }catch (NamingException ex) {
            throw new RemoteException(ex.getMessage());
        }
        return ds;
    }

    private ResultSet getResultSet() throws RemoteException,
            SQLException {
        return getDataSource().getConnection().createStatement().
                executeQuery(SQL_SELECT);
    }

    public List getScheduleItems() throws RemoteException {
        ResultSet rs = null;
        List list = new ArrayList(10);
        Map eventTypes = getEventTypes();
        try {
            rs = getResultSet();
            addItemsToList(rs, list, eventTypes);
        } catch (SQLException sqlx) {
            throw new RemoteException(sqlx.getMessage());
        } finally {
            try {
                rs.close();
                Statement s = rs.getStatement();
                s.close();
                s.getConnection().close();
            } catch (SQLException ignored) {
            }
        }
        return list;

    }

    private void addItemsToList(ResultSet rs, List list,
            Map eventTypes) throws SQLException {
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
    }

    private Map getEventTypes() throws RemoteException {
        EventTypeDBHome home = null;
        try {
            Context c = new InitialContext();
            Object o = c.lookup("EventTypeDB");
            home = (EventTypeDBHome) PortableRemoteObject.narrow(o,
                    EventTypeDBHome.class);
            EventTypeDB eventTypes = home.create();
            return eventTypes.getEventTypes();
        } catch (ClassCastException ex) {
            throw new RemoteException(ex.getMessage());
        } catch (NamingException ex) {
            throw new RemoteException(ex.getMessage());
        } catch (CreateException ce) {
            throw new RemoteException(ce.getMessage());
        }
    }

}