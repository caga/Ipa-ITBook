package com.nealford.art.schedwebwork.util;

public class ScheduleException extends Exception {
    private Throwable rootException;

    public ScheduleException() {
        super();
    }

    public ScheduleException(String msg) {
        super(msg);
    }

    public ScheduleException(Throwable root) {
        super(root.getMessage());
        rootException = root;
    }

    public Throwable getRootException() {
        return rootException;
    }

}