package com.nealford.art.facade.emotherearth.boundary;

import com.nealford.art.facade.emotherearth.util.DBPool;

public class BoundaryBase {
    private DBPool dBPool;

    public BoundaryBase() {
    }

    public DBPool getDBPool() {
        return dBPool;
    }

    public void setDBPool(DBPool dBPool) {
        this.dBPool = dBPool;
    }
}