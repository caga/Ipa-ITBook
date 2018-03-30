package com.nealford.art.ixbeans.servlet.entity;

public class ScheduleItemBizRules {
    private static ScheduleItemBizRules internalInstance;
    private static final int MIN_DURATION = 0;
    private static final int MAX_DURATION = 31;
    private static final String ERR_DURATION = "Duration must be " +
            "between " + MIN_DURATION + " and " + MAX_DURATION;
    private static final String ERR_TEXT = "Text must have a value";

    private ScheduleItemBizRules() {
    }

    public static ScheduleItemBizRules getInstance() {
        if (internalInstance == null)
            internalInstance = new ScheduleItemBizRules();
        return internalInstance;
    }

    public String validateDuration(int duration) {
        String result = null;
        if (duration < MIN_DURATION || duration > MAX_DURATION)
            result = ERR_DURATION;
        return result;
    }

    public String validateText(String text) {
        String result = null;
        if (text == null || text.length() == 0)
            result = ERR_TEXT;
        return result;
    }
}