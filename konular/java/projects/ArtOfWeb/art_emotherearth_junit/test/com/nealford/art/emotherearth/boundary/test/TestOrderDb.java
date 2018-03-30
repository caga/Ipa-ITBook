package com.nealford.art.emotherearth.boundary.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nealford.art.emotherearth.boundary.OrderDb;
import com.nealford.art.emotherearth.entity.Order;
import com.nealford.art.emotherearth.util.DBPool;
import com.nealford.art.emotherearth.util.test.TestShoppingCart;

public class TestOrderDb extends TestShoppingCart {
    private OrderDb orderDb = null;
    private int addedOrderKey;
    private DBPool dbPool;
    private Connection connection;
    private static final String SQL_DELETE_ORDER =
            "delete from orders where order_key = ?";
    private static final String SQL_SELECT_ORDER =
            "select * from orders where order_key = ?";
    private static final String DB_URL =
            "jdbc:mysql://localhost/eMotherEarth";
    private static final String DRIVER_CLASS =
            "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "marathon";
    private static final String TEST_CC_EXP = "11/1111";
    private static final String TEST_CC_NUM = "1111111111111111";
    private static final String TEST_CC_TYPE = "Visa";
    private static final String TEST_NAME = "Homer";
    private static final int TEST_USER_KEY = 1;

    public TestOrderDb(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        orderDb = new OrderDb();
        dbPool = new DBPool(DRIVER_CLASS, DB_URL, USER, PASSWORD);
        orderDb.setDbPool(dbPool);
        connection = dbPool.getConnection();
    }

    protected void tearDown() throws Exception {
        dbPool.release(connection);
        orderDb = null;
        super.tearDown();
    }

    public void testAddOrder() throws SQLException {
        Order actualOrder = new Order();
        actualOrder.setCcExp(TEST_CC_EXP);
        actualOrder.setCcNum(TEST_CC_NUM);
        actualOrder.setCcType(TEST_CC_TYPE);
        actualOrder.setUserKey(TEST_USER_KEY);
        orderDb.addOrder(shoppingCart, TEST_NAME, actualOrder);
        addedOrderKey = orderDb.getLastOrderKey();
        Order dbOrder = getOrderFromDatabase();
        assertEquals("cc num", actualOrder.getCcNum(),
                     dbOrder.getCcNum());
        assertEquals("cc exp", actualOrder.getCcExp(),
                     dbOrder.getCcExp());
        assertEquals("cc type", actualOrder.getCcType(),
                     dbOrder.getCcType());
        assertEquals("user key", actualOrder.getUserKey(),
                     dbOrder.getUserKey());
        deleteOrder(addedOrderKey);
    }

    private Order getOrderFromDatabase() {
        Order o = new Order();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_ORDER);
            ps.setInt(1, addedOrderKey);
            rs = ps.executeQuery();
            rs.next();
            o.setOrderKey(rs.getInt("order_key"));
            o.setUserKey(1);
            o.setCcExp(rs.getString("CC_EXP"));
            o.setCcNum(rs.getString("CC_NUM"));
            o.setCcType(rs.getString("CC_TYPE"));
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ignored) {
            }
        }
        return o;
    }


    private void deleteOrder(int addedOrderKey) {
        Connection c = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(SQL_DELETE_ORDER);
            ps.setInt(1, addedOrderKey);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
                throw new Exception("Delete failed");
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (c != null)
                    c.close();
            } catch (SQLException ignored) {
            }
        }

    }

}
