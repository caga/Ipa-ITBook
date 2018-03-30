package com.nealford.art.schedtapestry.page;

import com.nealford.art.schedtapestry.boundary.ScheduleDb;
import com.nealford.art.schedtapestry.entity.ScheduleItem;
import com.nealford.art.schedtapestry.util.ScheduleException;
import net.sf.tapestry.IMarkupWriter;
import net.sf.tapestry.IRequestCycle;
import net.sf.tapestry.form.IPropertySelectionModel;
import net.sf.tapestry.form.StringPropertySelectionModel;
import net.sf.tapestry.html.BasePage;
import org.apache.log4j.Logger;

public class Add extends BasePage {
    private static Logger logger = Logger.getLogger(Add.class);
    private String startDate;
    private int duration;
    private String eventType;
    private String description;
    private java.util.List errors;
    private String error;
    private ScheduleDb scheduleDb;
    private IPropertySelectionModel events;

    public void beginResponse(IMarkupWriter markupWriter,
                              IRequestCycle requestCycle) throws
            net.sf.tapestry.RequestCycleException {
        super.beginResponse(markupWriter, requestCycle);
        String dbUrl = getEngine().getSpecification().
                       getProperty("dbUrl");
        String driverClass = getEngine().getSpecification().
                             getProperty("driverClass");
        String user = getEngine().getSpecification().
                      getProperty("user");
        String password = getEngine().getSpecification().
                          getProperty("password");
        scheduleDb = new ScheduleDb(driverClass, dbUrl, user,
                                    password);
        events = new StringPropertySelectionModel(scheduleDb.
                listEventTypes());
    }

    public IPropertySelectionModel getEvents() {
        return events;
    }

    public void formSubmit(IRequestCycle cycle) {
        ScheduleItem item = new ScheduleItem(startDate, duration,
                description, eventType);
        errors = item.validate();
        if (errors.isEmpty()) {
            try {
                scheduleDb.addRecord(item);
            } catch (ScheduleException ex) {
                logger.error("AddPage.formSubmit()", ex);
            }
            cycle.setPage("Home");
        } else
            cycle.setPage("Add");
    }

    public java.util.List getErrors() {
        return errors;
    }

    public void setErrors(java.util.List errors) {
        this.errors = errors;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}