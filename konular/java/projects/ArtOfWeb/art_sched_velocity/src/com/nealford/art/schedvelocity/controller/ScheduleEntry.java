package com.nealford.art.schedvelocity.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nealford.art.schedvelocity.boundary.ScheduleDb;
import com.nealford.art.schedvelocity.entity.ScheduleItem;
import com.nealford.art.schedvelocity.util.ScheduleSaveBase;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class ScheduleEntry extends ScheduleSaveBase {


    public Template handleRequest(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Context context) throws
            ServletException, IOException {
        ScheduleDb scheduleDb =
                getBoundary(request.getSession(false));
        context.put("eventTypes", scheduleDb.getEventTypes());
        context.put("scheduleItem", new ScheduleItem());
        Template template = null;
        try {
            template = getTemplate("ScheduleEntryView.vm");
        } catch (ParseErrorException pex) {
            log("ScheduleEntryView: ", pex);
        } catch (ResourceNotFoundException rnfx) {
            log("ScheduleEntryView: ", rnfx);
        } catch (Exception x) {
            log("ScheduleEntryView: ", x);
        }
        return template;
    }
}