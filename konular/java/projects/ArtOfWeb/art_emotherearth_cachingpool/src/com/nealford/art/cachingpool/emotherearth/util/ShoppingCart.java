package com.nealford.art.cachingpool.emotherearth.util;

import java.util.Iterator;
import com.nealford.art.cachingpool.emotherearth.entity.CartItem;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.io.*;

public class ShoppingCart implements Serializable {
    private java.util.List itemList;
    private static final NumberFormat formatter =
            NumberFormat.getCurrencyInstance();

    public ShoppingCart() {
        itemList = new ArrayList(5);
    }

    public void addItem(CartItem ci) {
        itemList.add(ci);
    }

    public double getCartTotal() {
        Iterator it = itemList.iterator();
        double sum = 0;
        while (it.hasNext()) {
            sum += ((CartItem) it.next()).getExtendedPrice();
        }
        return sum;
    }

    public String getTotalAsCurrency() {
        return formatter.format(getCartTotal());
    }
    public java.util.List getItemList() {
        return itemList;
    }

}