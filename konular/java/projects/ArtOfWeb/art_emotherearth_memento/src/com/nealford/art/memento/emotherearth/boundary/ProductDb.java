package com.nealford.art.memento.emotherearth.boundary;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nealford.art.memento.emotherearth.entity.Product;
import com.nealford.art.memento.emotherearth.util.DBPool;

public class ProductDb implements Serializable {
    private List productList;
    private int recordsPerPage;
    private DBPool dbPool;
    private static final String SQL_ALL_PRODUCTS =
            "SELECT * FROM PRODUCTS";

    public ProductDb() {
        productList = new ArrayList(10);
    }

    public void setProductList(List productList) {
        this.productList = productList;
    }

    public List getProductList() {
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
                resultSet = s.executeQuery(SQL_ALL_PRODUCTS);
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


    public List getProductListSlice(int start, int recsPerPage) {
        List list = getProductList();
        if (start + recsPerPage > list.size()) {
            return list.subList(start, list.size());
        } else {
            return list.subList(start, start + recsPerPage);
        }
    }

    public void setDbPool(DBPool dbPool) {
        this.dbPool = dbPool;
    }

    public DBPool getDbPool() {
        return dbPool;
    }
}
