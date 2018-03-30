package com.nealford.art.history.customtags.stl;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Order implements Serializable {
    private int orderKey;
    private int userKey;
    private String orderStatus;
    private String shippingStatus;
    private String ccType;
    private String ccNum;
    private String ccExp;
    private DbPool dbPool;

    private static final String SQL_GET_USER_KEY =
        "SELECT ID FROM USERS WHERE NAME = ?";
    private static final String SQL_INSERT_ORDER =
        "INSERT INTO ORDERS (USER_KEY, CC_TYPE, CC_NUM, CC_EXP) " +
        "VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_GENERATED_KEY =
            "SELECT LAST_INSERT_ID()";

    public Order() {
    }

    public void setOrderKey(int orderKey) {
        this.orderKey = orderKey;
    }
    public int getOrderKey() {
        return orderKey;
    }
    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }
    public int getUserKey() {
        return userKey;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
    public String getShippingStatus() {
        return shippingStatus;
    }
    public void setCcType(String ccType) {
        this.ccType = ccType;
    }
    public String getCcType() {
        return ccType;
    }
    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }
    public String getCcNum() {
        return ccNum;
    }
    public void setCcExp(String ccExp) {
        this.ccExp = ccExp;
    }
    public String getCcExp() {
        return ccExp;
    }
    public void setDbPool(DbPool dbPool) {
        this.dbPool = dbPool;
    }
    public DbPool getDbPool() {
        return dbPool;
    }

    public void addOrder(ShoppingCart cart, String userName)
            throws SQLException {
        Connection c = null;
        try {
            c = dbPool.getConnection();
            c.setAutoCommit(false);
            int userKey = getUserKey(userName, c);
            addTheOrder(c);
            orderKey = getOrderKey(c);
            insertLineItems(cart, c);
            c.commit();

        } catch (SQLException sqlx) {
            c.rollback();
            throw sqlx;
        } finally {
            dbPool.release(c);
        }
    }

    private void insertLineItems(ShoppingCart cart, Connection c)
            throws SQLException {
        Iterator it = cart.getItemList().iterator();
        Lineitem li = new Lineitem();
        while (it.hasNext()) {
            ShoppingCartItem ci = (ShoppingCartItem) it.next();
            li.addLineItem(c, orderKey, ci.getItemId(),
                           ci.getQuantity());
        }
    }

    private int getOrderKey(Connection c) throws SQLException {
        ResultSet rs = null;
        Statement s = null;
        int orderKey = -1;
        try {
            s = c.createStatement();
            rs = s.executeQuery(SQL_GET_GENERATED_KEY);
            if (rs.next()) {
                orderKey = rs.getInt(1);
            } else {
                throw new SQLException(
                        "Order.addOrder(): no generated key");
            }
        } finally {
            rs.close();
            s.close();
        }
        return orderKey;
    }

    private void addTheOrder(Connection c) throws SQLException {
        int result = -1;
        PreparedStatement ps = c.prepareStatement(SQL_INSERT_ORDER);
        try {
        ps.setInt(1, userKey);
        ps.setString(2, ccType);
        ps.setString(3, ccNum);
        ps.setString(4, ccExp);
        result = ps.executeUpdate();
        if (result != 1)
            throw new SQLException(
                    "Order.addOrder(): order insert failed");
        } finally {
            ps.close();
        }
    }

    private int getUserKey(String userName, Connection c)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int userKey = -1;
        try {
            ps = c.prepareStatement(SQL_GET_USER_KEY);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new SQLException(
                        "Order.addOrder(): user not found");
            }
            userKey = rs.getInt(1);
        } finally {
            rs.close();
            ps.close();
        }
        return userKey;
    }

    public boolean isValidateCreditCardNumber(String ccNum) {
        boolean bad = (ccNum == null || ccNum.equals(""));
        return !bad;
    }

    public boolean isValidateCreditCardExp(String ccExp) {
        boolean bad = (ccExp == null || ccExp.equals(""));
        return !bad;
    }
}