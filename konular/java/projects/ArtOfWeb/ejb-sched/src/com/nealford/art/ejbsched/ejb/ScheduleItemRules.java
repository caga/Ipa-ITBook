package com.nealford.art.ejbsched.ejb;

import java.rmi.*;
import javax.ejb.*;
import java.util.List;
import com.nealford.art.ejbsched.model.ScheduleItem;

public interface ScheduleItemRules extends EJBObject {
    public List validate(ScheduleItem item) throws RemoteException;
}