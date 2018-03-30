package com.nealford.art.caching.emotherearth.util;

import com.nealford.art.caching.emotherearth.entity.Product;
import java.util.Iterator;
import java.util.List;

public class ProductCache {
    private List productCache;

    public ProductCache() {}

    public synchronized List getProductCache() {
        return productCache;
    }

    public synchronized List getProductSubset(int start,
                                              int recordsPerPage) {
        if ((start + recordsPerPage) > productCache.size())
            return productCache.subList(start, productCache.size());
        else
            return productCache.subList(start,
                                        start + recordsPerPage);
    }

    public synchronized int getNumberOfProducts() {
        return productCache.size();
    }

    public void setProductCache(List productCache) {
        this.productCache = productCache;
    }

    public Product getProduct(int id) {
        Iterator it = productCache.iterator();
        while (it.hasNext()) {
            Product p = (Product) it.next();
            if (p.getId() == id)
                return p;
        }

        return null;
    }
}
