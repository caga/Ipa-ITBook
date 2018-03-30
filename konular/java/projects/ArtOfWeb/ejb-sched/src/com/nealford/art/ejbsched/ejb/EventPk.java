package com.nealford.art.ejbsched.ejb;

import java.io.*;

public class EventPk implements Serializable {

    public int key;

    public EventPk() {
    }

    public EventPk(int key) {
        this.key = key;
    }

    public boolean equals(Object obj) {
        if (this.getClass().equals(obj.getClass())) {
            EventPk that = (EventPk) obj;
            return this.key == that.key;
        }
        return false;
    }

    public int hashCode() {
        return key;
    }
}