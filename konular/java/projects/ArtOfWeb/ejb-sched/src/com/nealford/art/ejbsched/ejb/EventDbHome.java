package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;

public interface EventDbHome extends EJBHome {
    public EventDb create() throws RemoteException, CreateException;
}