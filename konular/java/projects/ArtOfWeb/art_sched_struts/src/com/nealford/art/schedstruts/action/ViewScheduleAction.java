package com.nealford.art.schedstruts.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.nealford.art.schedstruts.boundary.ScheduleDb;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewScheduleAction extends Action {
    private static final String ERR_POPULATE =
            "SQL error: can't populate dataset";

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException,
            ServletException {

        DataSource dataSource = getDataSource(request);
        ScheduleDb sb = new ScheduleDb();
        sb.setDataSource(dataSource);

        try {
            sb.populate();
        } catch (SQLException sqlx) {
            System.out.println(sqlx);
            getServlet().getServletContext().log(ERR_POPULATE, sqlx);
        }
        request.setAttribute("scheduleBean", sb);
        return mapping.findForward("success");
    }
}