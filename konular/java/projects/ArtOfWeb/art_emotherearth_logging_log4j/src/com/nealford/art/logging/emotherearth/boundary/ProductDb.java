package com.nealford.art.logging.emotherearth.boundary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.nealford.art.logging.emotherearth.entity.Product;
import com.nealford.art.logging.emotherearth.util.DBPool;
import com.nealford.art.logging.emotherearth.util.PoolNotSetException;
import com.nealford.art.logging.emotherearth.util.ProductComparatorFactory;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;

public class ProductDb {
    private static Logger logger = Logger.getLogger(
        ProductDb.class.getPackage().getName());

    private List productList;
    private int recordsPerPage;
    private DBPool dbPool;
    private static final String SQL_ALL_PRODUCTS =
            "SELECT * FROM PRODUCTS";

    public ProductDb() {
        productList = new ArrayList(10);
        setupLogger();
    }

    private void setupLogger() {
        try {
            logger.addAppender(new FileAppender(new XMLLayout(),
                    "/temp/emotherearth.dblog.xml"));
        } catch (Exception x) {
            logger.fatal("Can't add handler", x);
        }
        logger.setLevel(Level.ALL);
    }

    public void setProductList(List productList) {
        this.productList = productList;
    }

    public List getProductList() {
        logger.info(this.getClass().getName() + ":getProductList");
        if (dbPool == null) {
            throw new PoolNotSetException(
                    "ProductDB.getProductList()");
        }
        int count = 0;
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
                    count++;
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
            logger.info("Number of records returned: " + count);
        }
        logger.info(this.getClass().getName() +":getProductList");
        return productList;
    }

    public Product getProduct(int id) {
        logger.info(this.getClass().getName() + ":getProduct");
        Iterator it = getProductList().iterator();
        while (it.hasNext()) {
            Product p = (Product) it.next();
            if (p.getId() == id) {
                logger.info("Found product: " + p);
                return p;
            }
        }
        logger.info("Product for id[" + id + "] not found");
        return null;
    }

    public List sortList(String criteria, List theList) {
        if (criteria != null) {
            ProductComparatorFactory comparatorFactory =
                    ProductComparatorFactory.getInstance();
            Comparator c =
                   comparatorFactory.getProductComparator(criteria);
            Collections.sort(theList, c);
        }
        return theList;

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
