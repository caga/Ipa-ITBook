package com.nealford.art.schedwebwork.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class AddScheduleEntryBeanInfo extends SimpleBeanInfo {
    private static final Logger logger = Logger.getLogger(
            AddScheduleEntryBeanInfo.class);
    static {
        try {
            logger.addAppender(new FileAppender(new SimpleLayout(),
                    "c:/temp/sched-webwork.log"));
        } catch (IOException ex) {
            logger.error("Can't create log file");
        }
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List list = new ArrayList();
            PropertyDescriptor descriptor;

            descriptor = new PropertyDescriptor("duration",
                    AddScheduleEntry.class);
            descriptor.setPropertyEditorClass(com.nealford.art.
                    schedwebwork.util.DurationEditor.class);
            list.add(descriptor);

            descriptor = new PropertyDescriptor("text",
                    AddScheduleEntry.class);
            descriptor.setPropertyEditorClass(com.nealford.art.
                    schedwebwork.util.TextEditor.class);
            list.add(descriptor);

            descriptor = new PropertyDescriptor("start",
                    AddScheduleEntry.class);
            descriptor.setPropertyEditorClass(com.nealford.art.schedwebwork.util.SimpleEditor.class);
            list.add(descriptor);

            descriptor = new PropertyDescriptor("eventType",
                    AddScheduleEntry.class);
            descriptor.setPropertyEditorClass(com.nealford.art.schedwebwork.util.SimpleEditor.class);
            list.add(descriptor);

            return (PropertyDescriptor[]) list.toArray(new
                    PropertyDescriptor[list.size()]);

        } catch (Exception x) {
            logger.error("AddScheduleEntryBeanInfo", x);
            return super.getPropertyDescriptors();
        }
    }

//    public BeanInfo[] getAdditionalBeanInfo() {
//        try {
//            return new BeanInfo[] {
//                    Introspector.getBeanInfo(AddScheduleEntry.class.
//                    getSuperclass())};
//        } catch (Exception x) {
//            logger.error("AddScheduleEntryBeanInfo", x);
//            return super.getAdditionalBeanInfo();
//
//        }
//    }
}