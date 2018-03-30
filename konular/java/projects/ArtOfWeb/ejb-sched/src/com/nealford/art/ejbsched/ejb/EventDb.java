package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import java.util.List;

public interface EventDb extends EJBObject {
    public List getScheduleItems() throws RemoteException;
}