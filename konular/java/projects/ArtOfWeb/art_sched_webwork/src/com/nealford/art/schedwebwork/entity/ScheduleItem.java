package com.nealford.art.schedwebwork.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class ScheduleItem implements Serializable {
    private static final int MAX_DURATION = 31;
    private static final int MIN_DURATION = 0;
    private static final String ERR_DURATION =
            "Duration must be between " + MIN_DURATION + " and " +
            MAX_DURATION;
    private static final String ERR_TEXT = "Text must have a value";
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

    public static String validateDuration(String duration) {
        int d = -1;
        String result = null;
        try {
             d = Integer.parseInt(duration);
        } catch (NumberFormatException x) {
            result = "Invalid number format: " + x;
        }
        if (d < MIN_DURATION || d > MAX_DURATION)
            result = ERR_DURATION;
        return result;
    }

    public static String validateText(String text) {
        return (text == null || text.length() < 1) ?
                ERR_TEXT :
                null;
    }

    public Map validate() {
        Map validationMessages = new HashMap();
        String err = validateDuration(String.valueOf(duration));
        if (err != null)
            validationMessages.put("Duration", err);
        err = validateText(text);
        if (err != null)
            validationMessages.put("Text", err);
        return validationMessages;
    }

}