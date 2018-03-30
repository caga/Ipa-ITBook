package com.nealford.art.facade.emotherearth.boundary;

import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.nealford.art.facade.emotherearth.entity.Product;
import java.sql.SQLException;
import java.io.*;
import java.util.List;
import java.util.Iterator;
import com.nealford.art.facade.emotherearth.util.Cacheable;

public class ProductDb extends BoundaryBase implements Cacheable, Serializable {
    private java.util.Vector productList;

    public ProductDb() {
        productList = new Vector(10);
    }

    public void setProductList(java.util.Vector productList) {
        this.productList = productList;
    }

    public java.util.Vector getProductList() {
        if (getDBPool() == null) {
            throw new RuntimeException("Pool property not set!");
        }
        if (productList.isEmpty()) {
            Connection c = null;
            Statement s = null;
            ResultSet resultSet = null;
            try {
                c = getDBPool().getConnection();
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
                    getDBPool().release(c);
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
        Vector list = getProductList();
        if (start + recsPerPage > list.size()) {
            return list.subList(start, list.size());
        } else {
            return list.subList(start, start + recsPerPage);
        }
    }

}

