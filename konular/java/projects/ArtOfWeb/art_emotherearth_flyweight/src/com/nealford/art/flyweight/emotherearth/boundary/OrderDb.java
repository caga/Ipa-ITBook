package com.nealford.art.flyweight.emotherearth.boundary;

import java.sql.Connection;
import java.sql.*;
import com.nealford.art.flyweight.emotherearth.util.*;
import java.util.*;
import com.nealford.art.flyweight.emotherearth.entity.Lineitem;
import com.nealford.art.flyweight.emotherearth.entity.CartItem;
import com.nealford.art.flyweight.emotherearth.entity.Order;

public class OrderDb {
    private static final String SQL_INSERT_LINEITEM =
        "INSERT INTO LINEITEMS (ORDER_KEY, ITEM_ID, QUANTITY)" +
        " VALUES(?, ?, ?)";
    private static final String SQL_GET_USER_KEY =
        "SELECT ID FROM USERS WHERE NAME = ?";
    private static final String SQL_INSERT_ORDER =
        "INSERT INTO ORDERS (USER_KEY, CC_TYPE, CC_NUM, CC_EXP) " +
        "VALUES (?, ?, ?, ?)";
    private int orderKey;
    private com.nealford.art.flyweight.emotherearth.util.DBPool dbPool;

    public OrderDb() {
    }

    public void addOrder(ShoppingCart cart, String userName,
            Order order) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = dbPool.getConnection();
            //-- get user key
            ps = c.prepareStatement(SQL_GET_USER_KEY);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Order.addOrder(): user not found");
            }
            int userKey = rs.getInt(1);

            //-- insert order
            //-- begin transaction
            s = c.createStatement();
            s.execute("BEGIN");

            ps = c.prepareStatement(SQL_INSERT_ORDER);
            ps.setInt(1, userKey);
            ps.setString(2, order.getCcType());
            ps.setString(3, order.getCcNum());
            ps.setString(4, order.getCcExp());
            int result = ps.executeUpdate();
            if (result != 1) {
                throw new SQLException("Order.addOrder(): order insert failed");
            }

            //-- get generated order key
            rs = s.executeQuery("SELECT LAST_INSERT_ID()");
            int orderKey = -1;
            if (rs.next()) {
                orderKey = rs.getInt(1);
                this.orderKey = orderKey;
            } else {
                throw new SQLException("Order.addOrder(): no generated key");
            }

            //-- insert lineitems
            Iterator it = cart.getItemList().iterator();
            Lineitem li = new Lineitem();
            while (it.hasNext()) {
                CartItem ci = (CartItem) it.next();
                addLineItem(c, orderKey, ci.getProduct().getId(),
                    ci.getQuantity());
            }

            //-- commit changes
            s = c.createStatement();
            s.executeUpdate("COMMIT");
            order.setOrderKey(orderKey);

        } catch (SQLException sqlx) {
            s = c.createStatement();
            s.executeUpdate("ROLLBACK");
            throw sqlx;
        } finally {
            try {
                dbPool.release(c);
                if (s != null) {
                    s.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }


    public void addLineItem(Connection c, int orderKey,
            int itemId, int quantity) throws SQLException {
        PreparedStatement ps = null;
        ps = c.prepareStatement(SQL_INSERT_LINEITEM);
        ps.setInt(1, orderKey);
        ps.setInt(2, itemId);
        ps.setInt(3, quantity);
        int result = ps.executeUpdate();
        if (result != 1) {
            throw new SQLException("Lineitem.addLineItem(): insert failed");
        }
    }
    public void setDbPool(com.nealford.art.flyweight.emotherearth.util.DBPool dbPool) {
        this.dbPool = dbPool;
    }
}