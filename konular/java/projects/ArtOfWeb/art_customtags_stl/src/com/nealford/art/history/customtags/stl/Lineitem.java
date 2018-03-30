package com.nealford.art.history.customtags.stl;

import java.io.*;
import java.sql.*;

public class Lineitem implements Serializable {
    private static final String SQL_INSERT_LINEITEM =
        "INSERT INTO LINEITEMS (ORDER_KEY, ITEM_ID, QUANTITY)" +
        " VALUES(?, ?, ?)";

    public Lineitem() {
    }

    public void setLineitemKey(int lineitemKey) {
        this.lineitemKey = lineitemKey;
    }
    public int getLineitemKey() {
        return lineitemKey;
    }
    public void setOrderKey(int orderKey) {
        this.orderKey = orderKey;
    }
    public int getOrderKey() {
        return orderKey;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
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
            throw new SQLException(
                    "Lineitem.addLineItem(): insert failed");
        }
    }
    private int lineitemKey;
    private int orderKey;
    private int itemId;
    private int quantity;
}