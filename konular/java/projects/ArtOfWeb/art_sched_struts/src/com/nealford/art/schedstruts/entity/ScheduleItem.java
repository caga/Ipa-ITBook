package com.nealford.art.schedstruts.entity;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class ScheduleItem extends org.apache.struts.validator.ValidatorForm
        implements Serializable {
    private String start;
    private int duration;
    private String text;
    private String eventType;
    private int eventTypeKey;

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
}