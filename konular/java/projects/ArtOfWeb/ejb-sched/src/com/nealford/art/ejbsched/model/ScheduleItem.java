package com.nealford.art.ejbsched.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.naming.Context;
import java.util.List;
import javax.rmi.PortableRemoteObject;
import javax.naming.*;
import com.nealford.art.ejbsched.ejb.*;

public class ScheduleItem implements Serializable {
    private String start;
    private int duration;
    private String text;
    private String eventType;
    private int eventTypeKey;
    private Context context;
    private static final String EJB_HOME_NAME = "ScheduleItemRules";

    public ScheduleItem(String start, int duration, String text,
            String eventType, int eventTypeKey) {
        this.start = start;
        this.duration = duration;
        this.text = text;
        this.eventType = eventType;
        this.eventTypeKey = eventTypeKey;
    }

    public ScheduleItem() {
    }

    public void setStart(String newStart) {
        start = newStart;
    }

    public String getStart() {
        return start;
    }

    public void setDuration(int newDuration) {
        duration = newDuration;
    }

    public int getDuration() {
        return duration;
    }

    public void setText(String newText) {
        text = newText;
    }

    public String getText() {
        return text;
    }

    public void setEventType(String newEventType) {
        eventType = newEventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventTypeKey(int eventTypeKey) {
        this.eventTypeKey = eventTypeKey;
    }

    public int getEventTypeKey() {
        return eventTypeKey;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List validate() {
        try {
            Object o = context.lookup(EJB_HOME_NAME);
            ScheduleItemRulesHome home = (ScheduleItemRulesHome)
                                         PortableRemoteObject.narrow(o,
                                         ScheduleItemRulesHome.class);
            ScheduleItemRules rules = home.create();
            System.err.println(rules.toString());
            return rules.validate(this);
        } catch (Exception ex) {
            List errors = new ArrayList();
            errors.add("EJB exception: " + ex);
            return errors;
        }
    }

}