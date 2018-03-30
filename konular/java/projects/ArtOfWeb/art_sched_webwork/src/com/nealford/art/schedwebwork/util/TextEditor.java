package com.nealford.art.schedwebwork.util;

import webwork.action.ValidationEditorSupport;
import com.nealford.art.schedwebwork.entity.*;


public class TextEditor extends ValidationEditorSupport {
    public void setAsText(String txt) {
        String error = ScheduleItem.validateText(txt);
        if (error != null)
            throw new IllegalArgumentException(error);
        setValue(txt);
    }

}