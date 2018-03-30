package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;

public interface Event extends EJBObject {
    public void setStart(String start) throws RemoteException;
    public String getStart() throws RemoteException;
    public void setDuration(int duration) throws RemoteException;
    public int getDuration() throws RemoteException;
    public void setText(String text) throws RemoteException;
    public String getText() throws RemoteException;
    public void setEventType(int eventType) throws RemoteException;
    public int getEventType() throws RemoteException;
    public void setEventKey(int eventKey) throws RemoteException;
    public int getEventKey() throws RemoteException;
}