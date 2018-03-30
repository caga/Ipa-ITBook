package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;

public interface ScheduleItemRulesHome extends EJBHome {
    public ScheduleItemRules create() throws RemoteException, CreateException;
}