package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import java.sql.*;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.*;
import javax.sql.*;

public class EventTypeDBBean implements SessionBean {
    private SessionContext sessionContext;
    private static final String SQL_EVENT_TYPES =
            "SELECT event_type_key, event_text FROM event_types";

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

    public Map getEventTypes() throws RemoteException {
        Map eventTypes = new HashMap();
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            con = getDataSource().getConnection();
            s = con.createStatement();
            rs = s.executeQuery(SQL_EVENT_TYPES);
            eventTypes = new HashMap();
            while (rs.next())
                eventTypes.put(rs.getObject("event_type_key"),
                               rs.getString("event_text"));
        } catch (SQLException sqlx) {
            throw new RemoteException(sqlx.getMessage());
        } finally {
            try {
                rs.close();
                s.close();
                con.close();
            } catch (Exception ignored) {
            }
            return eventTypes;
        }
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
}