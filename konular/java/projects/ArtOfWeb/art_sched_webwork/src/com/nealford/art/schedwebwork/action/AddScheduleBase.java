package com.nealford.art.schedwebwork.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import com.nealford.art.schedwebwork.boundary.ScheduleDb;
import com.nealford.art.schedwebwork.entity.ScheduleItem;
import webwork.action.ActionContext;
import webwork.action.ActionSupport;

public class AddScheduleBase extends ActionSupport {
    private List events;
    protected ScheduleItem scheduleItem;
    private ScheduleDb scheduleDb;
    private String driverClass;
    private String dbUrl;
    private String user;
    private String password;

    public AddScheduleBase() {
        buildEventList();
        scheduleItem = new ScheduleItem();
    }

    public List getEvents() {
        return events;
    }

    private void buildEventList() {
        events = new ArrayList(5);
        scheduleDb = getScheduleBoundary();
        Map m = scheduleDb.getEventTypes();
        Iterator it = m.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            EventType et = new EventType();
            et.setKey(((Integer) entry.getKey()).intValue());
            et.setEvent((String) entry.getValue());
            events.add(et);
        }
    }

    protected ScheduleDb getScheduleBoundary() {
        getDatabaseConfigurationParameters();
        return new ScheduleDb(driverClass, dbUrl, user, password);
    }


    private void getDatabaseConfigurationParameters() {
        ActionContext ac = ActionContext.getContext();
        ServletContext sc = ac.getServletContext();
        driverClass = sc.getInitParameter("driverClass");
        dbUrl = sc.getInitParameter("dbUrl");
        user = sc.getInitParameter("user");
        password = sc.getInitParameter("password");
    }

    public class EventType {
        private int key;
        private String event;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }
    }

    public String getStart() {
        return scheduleItem.getStart();
    }

    public void setStart(String start) {
        scheduleItem.setStart(start);
    }

    public String getDuration() {
        return String.valueOf(scheduleItem.getDuration());
    }

    public void setDuration(String duration) {
        scheduleItem.setDuration(Integer.parseInt(duration));
    }

    public String getText() {
        return scheduleItem.getText();
    }

    public void setText(String text) {
        scheduleItem.setText(text);
    }

    public String getEventType() {
        return scheduleItem.getEventType();
    }

    public void setEventType(String eventType) {
        scheduleItem.setEventType(eventType);
    }
}