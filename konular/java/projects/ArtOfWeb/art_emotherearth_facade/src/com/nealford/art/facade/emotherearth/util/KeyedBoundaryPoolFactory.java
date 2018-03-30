package com.nealford.art.facade.emotherearth.util;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import com.nealford.art.facade.emotherearth.boundary.ProductDb;
import com.nealford.art.facade.emotherearth.boundary.OrderDb;

public class KeyedBoundaryPoolFactory
        implements KeyedPoolableObjectFactory {

    public Object makeObject(Object key) {
        if (key.equals(com.nealford.art.facade.emotherearth.
                       boundary.ProductDb.class)) {
            return new ProductDb();
        } else if (key.equals(com.nealford.art.facade.emotherearth.
                              boundary.OrderDb.class)) {
            return new OrderDb();
        } else
            return null;
    }

    public void destroyObject(Object key, Object obj) {
        obj = null;
    }

    public boolean validateObject(Object key, Object obj) {
        return true;
    }

    public void activateObject(Object key, Object obj)  {
    }

    public void passivateObject(Object key, Object obj)  {
    }
}