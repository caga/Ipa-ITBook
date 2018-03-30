package com.nealford.art.caching.emotherearth.boundary;

import com.nealford.art.caching.emotherearth.entity.Product;
import com.nealford.art.caching.emotherearth.util.DBPool;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDb implements Serializable {

    public List getProductList(DBPool dbPool) {
        List productList = new ArrayList(20);
        if (dbPool == null)
            throw new RuntimeException("Pool property not set!");

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
            } catch (SQLException ignored) {}
        }

        return productList;
    }
}
