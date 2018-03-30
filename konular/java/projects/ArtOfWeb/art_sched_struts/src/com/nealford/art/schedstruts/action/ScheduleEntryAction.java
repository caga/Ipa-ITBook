package com.nealford.art.schedstruts.action;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import org.apache.struts.action.*;
import javax.sql.DataSource;
import com.nealford.art.schedstruts.boundary.*;

public class ScheduleEntryAction extends Action {
    private static final String ERR_DATASOURCE_NOT_SET =
            "ScheduleEntryAction: DataSource not set";

    public ActionForward execute(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException,
            ServletException {

        ScheduleDb sb = new ScheduleDb();
        DataSource ds = getDataSource(request);
        if (ds == null)
            throw new ServletException(ERR_DATASOURCE_NOT_SET);
        sb.setDataSource(ds);
        //-- place the scheduleBean on the session in case the
        //-- update must redirect back to the JSP -- it must be
        //-- able to pull the scheduleBean from the session, not
        //-- the request
        HttpSession session = request.getSession(true);
        session.setAttribute("eventTypes", sb.getEventTypeLabels());
        return mapping.findForward("success");
    }
}