package com.nealford.art.cachingpool.emotherearth.boundary;

import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.nealford.art.cachingpool.emotherearth.entity.Product;
import java.sql.SQLException;
import java.io.*;
import java.util.List;
import java.util.Iterator;

public class ProductDb implements Serializable {
    private java.util.Vector productList;
    private int recordsPerPage;
    private com.nealford.art.cachingpool.emotherearth.util.DBPool dbPool;

    public ProductDb() {
        productList = new Vector(10);
    }

    public void setProductList(java.util.Vector productList) {
        this.productList = productList;
    }

    public java.util.Vector getProductList() {
        if (dbPool == null) {
            throw new RuntimeException("Pool property not set!");
        }
        if (productList.isEmpty()) {
            Connection c = null;
            Statement s = null;
            ResultSet resultSet = null;
            try {
                c = dbPool.getConnection();
                s = c.createStatement();
                resultSet = s.executeQuery("SELECT * FROM PRODUCTS");
                while (resultSet.next()) {
                    Product p = new Product();
                    p.setId(resultSet.getInt("ID"));
                    p.setName(resultSet.getString("NAME"));
                    p.setPrice(resultSet.getDouble("PRICE"));
                    productList.add(p);
                }
            } catch (SQLException sqlx) {
                throw new RuntimeException(sqlx.getMessage());
            } finally {
                try {
                    dbPool.release(c);
                    resultSet.close();
                    s.close();
                } catch (SQLException ignored) {
                }
            }

        }
        return productList;
    }

    public Product getProduct(int id) {
        Iterator it = getProductList().iterator();
        while (it.hasNext()) {
            Product p = (Product) it.next();
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public List getProductListSlice(int start) {
        Vector list = getProductList();
        if (start + getRecordsPerPage() > list.size()) {
            return list.subList(start, list.size());
        } else {
            return list.subList(start, start + getRecordsPerPage());
        }
    }

    public void setDbPool(com.nealford.art.cachingpool.emotherearth.util.DBPool dbPool) {
        this.dbPool = dbPool;
    }

    public com.nealford.art.cachingpool.emotherearth.util.DBPool getDbPool() {
        return dbPool;
    }
}

