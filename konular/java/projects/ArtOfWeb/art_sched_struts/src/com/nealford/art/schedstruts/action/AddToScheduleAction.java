package com.nealford.art.schedstruts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nealford.art.schedstruts.boundary.ScheduleDb;
import com.nealford.art.schedstruts.entity.ScheduleItem;
import com.nealford.art.schedstruts.util.ScheduleAddException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddToScheduleAction extends Action {
    private static final String ERR_INSERT =
            "AddToScheduleAction: SQL Insert error";

    public ActionForward execute(ActionMapping mapping,
            ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException,
            ServletException {

        ScheduleDb sb = new ScheduleDb();
        sb.setDataSource(getDataSource(request));

        ScheduleItem si = (ScheduleItem) actionForm;
        try {
            sb.addRecord(si);
        } catch (ScheduleAddException sax) {
            getServlet().getServletContext().log(ERR_INSERT, sax);
            sax.printStackTrace();
        }

        //-- clean up extraneous session reference to eventTypes
        HttpSession session = request.getSession(false);
        if (session != null)
            session.removeAttribute("eventTypes");

        return mapping.findForward("success");
    }
}