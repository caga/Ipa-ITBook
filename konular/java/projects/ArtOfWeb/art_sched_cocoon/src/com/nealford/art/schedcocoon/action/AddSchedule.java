package com.nealford.art.schedcocoon.action;

import org.apache.cocoon.acting.*;
import org.apache.cocoon.environment.*;
import java.util.*;
import org.apache.avalon.framework.parameters.*;
import javax.servlet.http.*;
import com.nealford.art.schedcocoon.entity.*;
import com.nealford.art.schedcocoon.boundary.*;

public class AddSchedule extends AbstractAction {

    public Map act(Redirector redirector, SourceResolver resolver,
                   Map objectModel, String source, Parameters par) throws
            java.lang.Exception {
        Request request = ObjectModelHelper.getRequest(objectModel);
        Map siteMapParams = new HashMap();
        ScheduleDb scheduleDb = new ScheduleDb();
        Map eventMap = scheduleDb.getEventTypes();

        String start = request.getParameter("start");
        if (start == null) {
            request.setAttribute("eventMap", eventMap);
            siteMapParams.put("dest", "add");
            return siteMapParams;
        }

        int duration = Integer.parseInt(request.getParameter("duration"));
        String text = request.getParameter("text");
        int eventTypeKey = Integer.parseInt(request.getParameter("eventTypeKey"));
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setText(text);
        scheduleItem.setDuration(duration);
        scheduleItem.setStart(start);
        scheduleItem.setEventTypeKey(eventTypeKey);

        List errors = scheduleItem.validate();
        if (errors.isEmpty()) {
            scheduleDb.addRecord(scheduleItem);
            redirector.redirect(true, "viewschedule");
        }
        else {
            request.setAttribute("errors", errors);
            request.setAttribute("scheduleItem", scheduleItem);
            request.setAttribute("eventMap", eventMap);
            siteMapParams.put("dest", "add");
            return siteMapParams;
        }

        return siteMapParams;
    }

}