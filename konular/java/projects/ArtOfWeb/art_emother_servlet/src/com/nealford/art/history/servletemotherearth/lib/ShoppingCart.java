package com.nealford.art.history.servletemotherearth.lib;

import java.util.*;
import java.text.NumberFormat;

public class ShoppingCart {

    public ShoppingCart() {
    }

    public String toHtmlTable() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        StringBuffer out = new StringBuffer();
        out.append("<TABLE border=1>\n");

        //-- build titles
        out.append("<TR>");
        out.append("<TH> ID");
        out.append("<TH> Name");
        out.append("<TH> Quantity");
        out.append("<TH> Price");
        out.append("<TH> Total");
        out.append("</TR>\n");

        Iterator it = items.iterator();
        while (it.hasNext()) {
            ShoppingCartItem item = (ShoppingCartItem) it.next();
            out.append("<TR>");
            out.append("<TD> " + item.getItemId());
            out.append("<TD> " + item.getItemName());
            out.append("<TD> " + item.getQuantity());
            out.append("<TD align='right'> " +
                       formatter.format(item.getItemPrice()));
            out.append("<TD align='right'> " +
                       formatter.format(item.getTotal()));
            out.append("</TR>\n");
        }
        out.append("</TABLE>\n");

        return out.toString();
    }

    public void addItem(ShoppingCartItem sci) {
        items.add(sci);
    }

    public double getCartTotal() {
        Iterator it = items.iterator();
        double sum = 0;
        while (it.hasNext()) {
            sum += ((ShoppingCartItem) it.next()).getExtendedPrice();
        }
        return sum;
    }

    public List getItemList() {
        return items;
    }

    public String getTotalAsCurrency() {
        return NumberFormat.getCurrencyInstance().format(getCartTotal());
    }

    private List items = new Vector(5);
}


