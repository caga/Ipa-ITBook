package com.nealford.art.schedcocoon.action;

import java.util.HashMap;
import java.util.Map;
import com.nealford.art.schedcocoon.boundary.ScheduleDb;
import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.acting.AbstractAction;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;

public class ViewSchedule extends AbstractAction {

    public Map act(Redirector redirector, SourceResolver resolver,
                   Map objectModel, String source, Parameters par)
            throws java.lang.Exception {
        ScheduleDb scheduleDb = new ScheduleDb();
        scheduleDb.populate();
        Request request = ObjectModelHelper.getRequest(objectModel);
        request.setAttribute("scheduleItemList",
                             scheduleDb.getList());
        request.setAttribute("columnHeaders",
                             generateDisplayColumns(scheduleDb));
        return EMPTY_MAP;
    }

    private String[] generateDisplayColumns(ScheduleDb scheduleDb) {
        int numDisplayHeaders =
                scheduleDb.getDisplayColumnHeaders().length;
        String[] displayColumns = new String[numDisplayHeaders - 1];
        System.arraycopy(scheduleDb.getDisplayColumnHeaders(), 1,
                         displayColumns, 0, numDisplayHeaders - 1);
        return displayColumns;
    }

}