package com.nealford.art.ixbeans.servlet.db;

public class SqlRes extends java.util.ListResourceBundle {
    private static final Object[][] contents = new String[][]{
            { "event", "select * from event" },
            { "event_types", "select * from event_types" }};
    public Object[][] getContents() {
        return contents;
    }
}