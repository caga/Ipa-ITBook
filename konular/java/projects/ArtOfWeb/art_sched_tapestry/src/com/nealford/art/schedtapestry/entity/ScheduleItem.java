package com.nealford.art.schedtapestry.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleItem implements Serializable {
    private String start;
    private int duration;
    private String text;
    private String eventType;

    public ScheduleItem(String start, int duration, String text,
            String eventType) {
        this.start = start;
        this.duration = duration;
        this.text = text;
        this.eventType = eventType;
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

    public List validate() {
        List validationMessages = new ArrayList(0);
        if (duration < 0 || duration > 31)
            validationMessages.add("Invalid duration");
        if (text == null || text.length() < 1)
            validationMessages.add("Event must have description");
        return validationMessages;
    }

}