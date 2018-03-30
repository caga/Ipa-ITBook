package com.nealford.art.ejbsched.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.util.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import com.nealford.art.ejbsched.ejb.*;
import java.rmi.*;
import javax.ejb.*;

public class ScheduleBean {
    private List scheduleItems;
    private Map eventTypes;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void populate() {
        try {

            Object o = context.lookup("EventDb");
            EventDbHome home =
                    (EventDbHome) PortableRemoteObject.narrow(o,
                    EventDbHome.class);
            EventDb eventDb = home.create();
            scheduleItems = eventDb.getScheduleItems();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (CreateException ex) {
            ex.printStackTrace();
        }
    }

    public void addRecord(ScheduleItem item) throws
            ScheduleAddException {
        try {
            Context context = new InitialContext();
            Object o = context.lookup("Event");
            EventHome home =
                    (EventHome) PortableRemoteObject.narrow(o,
                    EventHome.class);
            home.create(item.getStart(),
                        item.getDuration(),
                        item.getText(),
                        item.getEventTypeKey());

        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (CreateException ex) {
            ex.printStackTrace();
        }

    }

    public Map getEventTypes() {
        try {
            if (eventTypes == null) {
                Context context = new InitialContext();
                Object o = context.lookup("EventTypeDB");
                EventTypeDBHome home =
                        (EventTypeDBHome)
                        PortableRemoteObject.narrow(o,
                        EventTypeDBHome.class);
                EventTypeDB eventTypeDB = home.create();
                eventTypes = eventTypeDB.getEventTypes();
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (CreateException ex) {
            ex.printStackTrace();
        }
        return eventTypes;
    }

    public List getScheduleItems() {
        return scheduleItems;
    }


}