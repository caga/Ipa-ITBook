package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;

public interface EventHome extends EJBHome {
    public Event create(String start, int duration, String text,
                        int eventType) throws
                        RemoteException, CreateException;
    public Event findByPrimaryKey(EventPk primKey) throws
            ObjectNotFoundException, RemoteException,
            FinderException;
}