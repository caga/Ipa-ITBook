package com.nealford.art.schedwebwork.util;

import com.nealford.art.schedwebwork.entity.ScheduleItem;
import webwork.action.ValidationEditorSupport;

public class DurationEditor extends ValidationEditorSupport {

    public void setAsText(String txt) {
        String error = ScheduleItem.validateDuration(txt);
        if (error != null)
            throw new IllegalArgumentException(error);
        setValue(txt);
    }
}