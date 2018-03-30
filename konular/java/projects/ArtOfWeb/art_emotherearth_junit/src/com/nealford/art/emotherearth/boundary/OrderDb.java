package com.nealford.art.emotherearth.boundary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.nealford.art.emotherearth.entity.CartItem;
import com.nealford.art.emotherearth.entity.Order;
import com.nealford.art.emotherearth.util.ShoppingCart;
import com.nealford.art.emotherearth.util.DBPool;

public class OrderDb {
    private static final String SQL_INSERT_LINEITEM =
            "INSERT INTO LINEITEMS (ORDER_KEY, ITEM_ID, QUANTITY)" +
            " VALUES(?, ?, ?)";
    private static final String SQL_GET_USER_KEY =
            "SELECT ID FROM USERS WHERE NAME = ?";
    private static final String SQL_INSERT_ORDER =
            "INSERT INTO ORDERS (USER_KEY, CC_TYPE, CC_NUM, CC_EXP)"
            + "VALUES (?, ?, ?, ?)";
    private DBPool dbPool;
    private int lastOrderKey;

    public void addOrder(ShoppingCart cart, String userName,
                         Order order) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        Statement s = null;
        ResultSet rs = null;
        boolean transactionState = false;
        try {
            c = dbPool.getConnection();
            transactionState = c.getAutoCommit();
            int userKey = getUserKey(userName, c, ps, rs);
            c.setAutoCommit(false);
            addSingleOrder(order, c, ps, userKey);
            s = c.createStatement();
            int orderKey = getOrderKey(s, rs);
            addLineItems(cart, c, orderKey);
            c.commit();
            order.setOrderKey(orderKey);
            lastOrderKey = orderKey;

        } catch (SQLException sqlx) {
            s = c.createStatement();
            c.rollback();
            throw sqlx;
        } finally {
            try {
                c.setAutoCommit(transactionState);
                dbPool.release(c);
                if (s != null)
                    s.close();
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException ignored) {
            }
        }
    }

    private void addLineItems(ShoppingCart cart, Connection c,
                              int orderKey) throws SQLException {
        Iterator it = cart.getItemList().iterator();
        while (it.hasNext()) {
            CartItem ci = (CartItem) it.next();
            addLineItem(c, orderKey, ci.getProduct().getId(),
                        ci.getQuantity());
        }
    }

    private int getOrderKey(Statement s, ResultSet rs) throws
            SQLException {
        rs = s.executeQuery("SELECT LAST_INSERT_ID()");
        int orderKey = -1;
        if (rs.next())
            orderKey = rs.getInt(1);
        else
            throw new SQLException(
                    "Order.addOrder(): no generated key");
        return orderKey;
    }

    private void addSingleOrder(Order order, Connection c,
                                       PreparedStatement ps,
                                       int userKey) throws
            SQLException {
        ps = c.prepareStatement(SQL_INSERT_ORDER);
        ps.setInt(1, userKey);
        ps.setString(2, order.getCcType());
        ps.setString(3, order.getCcNum());
        ps.setString(4, order.getCcExp());
        int result = ps.executeUpdate();
        if (result != 1) {
            throw new SQLException(
                    "Order.addOrder(): order insert failed");
        }
    }

    private int getUserKey(String userName, Connection c,
                           PreparedStatement ps, ResultSet rs) throws
            SQLException {
        ps = c.prepareStatement(SQL_GET_USER_KEY);
        ps.setString(1, userName);
        rs = ps.executeQuery();
        if (!rs.next()) {
            throw new SQLException(
                    "Order.addOrder(): user not found");
        }
        int userKey = rs.getInt(1);
        return userKey;
    }


    public void addLineItem(Connection c, int orderKey,
                            int itemId, int quantity) throws
            SQLException {
        PreparedStatement ps = null;
        ps = c.prepareStatement(SQL_INSERT_LINEITEM);
        ps.setInt(1, orderKey);
        ps.setInt(2, itemId);
        ps.setInt(3, quantity);
        int result = ps.executeUpdate();
        if (result != 1) {
            throw new SQLException(
                    "Lineitem.addLineItem(): insert failed");
        }
    }

    public void setDbPool(com.nealford.art.emotherearth.util.DBPool
                          dbPool) {
        this.dbPool = dbPool;
    }

    public int getLastOrderKey() {
        return lastOrderKey;
    }
}