package com.nealford.art.logging.emotherearth.util;

import java.util.Comparator;
import com.nealford.art.logging.emotherearth.entity.Product;

public class IdComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        Product p1 = (Product) o1;
        Product p2 = (Product) o2;
        return p1.getId() - p2.getId();
    }

    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
