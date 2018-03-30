package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;

public interface EventTypeDBHome extends EJBHome {
    public EventTypeDB create() throws RemoteException, CreateException;
}