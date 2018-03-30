package com.nealford.art.emotherearth.ws;

import java.sql.SQLException;
import com.nealford.art.emotherearth.boundary.OrderDb;
import com.nealford.art.emotherearth.util.ConnectionPoolProxy;
import com.nealford.art.emotherearth.util.DBPool;

public class OrderInfo {

    public String getWsDescription() {
        return "eMotherEarth order information";
    }

    public String getOrderStatus(int orderKey) {
        OrderDb orderDb = new OrderDb();
        orderDb.setDbPool(getConnectionPool());
        try {
            return orderDb.getOrderStatus(orderKey);
        } catch (SQLException ex) {
            return "error accessing status: " + ex.getMessage();
        }
    }

    public String getShippingStatus(int orderKey) {
        OrderDb orderDb = new OrderDb();
        orderDb.setDbPool(getConnectionPool());
        try {
            return orderDb.getShippingStatus(orderKey);
        } catch (SQLException ex) {
            return "error accessing status: " + ex.getMessage();
        }
    }

    private DBPool getConnectionPool() {
        return ConnectionPoolProxy.getInstance().getDbPool();
    }

}