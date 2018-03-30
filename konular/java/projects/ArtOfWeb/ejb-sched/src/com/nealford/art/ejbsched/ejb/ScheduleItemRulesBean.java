package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import java.util.*;
import com.nealford.art.ejbsched.model.ScheduleItem;

public class ScheduleItemRulesBean implements SessionBean {
    private SessionContext sessionContext;
    static private final int MIN_DURATION = 0;
    static private final int MAX_DURATION = 31;

    public void ejbCreate() {
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public List validate(ScheduleItem item) {
        List validationMessages = new ArrayList(0);
        if (item.getDuration() < MIN_DURATION ||
                item.getDuration() > MAX_DURATION)
            validationMessages.add("Invalid duration");
        if (item.getText() == null || item.getText().length() < 1)
            validationMessages.add("Event must have description");
        return validationMessages;
    }
}