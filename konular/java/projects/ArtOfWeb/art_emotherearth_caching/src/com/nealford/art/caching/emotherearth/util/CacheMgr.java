package com.nealford.art.caching.emotherearth.util;

import java.util.List;

public class CacheMgr {
    private List productCache;

    public CacheMgr() {
    }

    public List getProductCache() {
        return productCache;
    }

    public List getProductSubsetStartingWith(int start) {
        return null;
    }

    public void setProductCache(List productCache) {
        this.productCache = productCache;
    }
}