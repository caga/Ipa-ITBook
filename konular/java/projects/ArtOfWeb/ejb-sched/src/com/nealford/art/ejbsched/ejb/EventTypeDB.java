package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import java.util.Map;

public interface EventTypeDB extends EJBObject {
    public Map getEventTypes() throws RemoteException;
}