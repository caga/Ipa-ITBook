package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import javax.sql.DataSource;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.naming.NamingException;

public class EventBean implements EntityBean {
    private EntityContext entityContext;
    private static final String SQL_SELECT = "SELECT * FROM event" +
            " where event_key = ?";
    private static final String SQL_INSERT =
            "INSERT INTO event (start, duration, description, " +
            "event_type) VALUES(?, ?, ?, ?)";
    private static final String SQL_EVENT_TYPES =
            "SELECT event_type_key, event_text FROM event_types";
    private static final String SQL_UPDATE =
            "UPDATE event SET start = ?, duration = ?, " +
            "description = ?, event_type = ? WHERE event_key = ?";
    private static final String SQL_DELETE =
            "DELETE FROM event WHERE event_key = ?";
    private static final String SQL_LAST_INSERT_ID =
            "SELECT distinct last_insert_id() k from event";
    public String start;
    public int duration;
    public String text;
    public int eventType;
    public int eventKey;



    public EventPk ejbCreate(String start, int duration,
                             String text, int eventType)
                             throws CreateException {
        this.start = start;
        this.duration = duration;
        this.text = text;
        this.eventType = eventType;
        Connection con = null;
        PreparedStatement ps = null;
        Statement s = null;
        ResultSet rs = null;
        int newKey = -1;
        try {
            con = getDataSource().getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, start);
            ps.setInt(2, duration);
            ps.setString(3, text);
            ps.setInt(4, eventType);
            if (ps.executeUpdate() != 1) {
                throw new CreateException("Insert failed");
            }

            //-- get the generated id
            s = con.createStatement();
            rs = s.executeQuery(SQL_LAST_INSERT_ID);
            rs.next();
            newKey = rs.getInt("k");

        } catch (SQLException sqlx) {
            throw new CreateException(sqlx.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (s != null)
                    s.close();
                if (con != null)
                    con.close();
            } catch (Exception ignored) {
            }
        }

        EventPk eventPk = new EventPk();
        eventPk.key = newKey;
        return eventPk;
    }

    public void ejbPostCreate(String start, int duration,
                              String text, int eventType)
                              throws CreateException {
    }

    public void ejbLoad() {
        EventPk eventPk = (EventPk) entityContext.getPrimaryKey();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDataSource().getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, eventPk.key);
            rs = ps.executeQuery();
            start = rs.getString("start");
            duration = rs.getInt("duration");
            text = rs.getString("text");
            eventType = rs.getInt("event_type");


        } catch (SQLException sqlx) {
            throw new EJBException(sqlx.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void ejbStore() {
        EventPk eventPk = (EventPk) entityContext.getPrimaryKey();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDataSource().getConnection();
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, start);
            ps.setInt(2, duration);
            ps.setString(3, text);
            ps.setInt(4, eventType);
            ps.setInt(5, eventPk.key);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
                throw new EJBException("Update failed");
        } catch (SQLException sqlx) {
            throw new EJBException(sqlx.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void ejbRemove() throws RemoveException {
        EventPk eventPk = (EventPk) entityContext.getPrimaryKey();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDataSource().getConnection();
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, eventPk.key);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
                throw new EJBException("Delete failed");
        } catch (SQLException sqlx) {
            throw new EJBException(sqlx.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception ignored) {
            }
        }

    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public void unsetEntityContext() {
        entityContext = null;
    }

    public EventPk ejbFindByPrimaryKey(EventPk primKey) throws ObjectNotFoundException {
        /**@todo Implement this method*/
        return null;
    }

    private DataSource getDataSource() throws EJBException {
        DataSource ds = null;
        try {
            Context c = new InitialContext();
            Object o = c.lookup("java:/MySQLDS");
            ds = (DataSource) PortableRemoteObject.narrow(o,
                    DataSource.class);
        }
        catch (ClassCastException ex) {
            throw new EJBException(ex.getMessage());
        }catch (NamingException ex) {
            throw new EJBException(ex.getMessage());
        }
        return ds;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventKey(int eventKey) {
        this.eventKey = eventKey;
    }

    public int getEventKey() {
        return eventKey;
    }
}