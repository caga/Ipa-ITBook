package com.nealford.art.history.servletemotherearth.lib;

import java.text.NumberFormat;

public class ShoppingCartItem {
    private int itemId;
    private int quantity;
    private String itemName;
    private double itemPrice;

    public ShoppingCartItem(int id, String name, int qty, double price) {
        this.itemId = id;
        this.itemName = name;
        this.quantity = qty;
        this.itemPrice = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int newItemId) {
        itemId = newItemId;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemName(String newItemName) {
        itemName = newItemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemPrice(double newItemPrice) {
        itemPrice = newItemPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getTotal() {
        return quantity * itemPrice;
    }

    public double getExtendedPrice() {
        return itemPrice * quantity;
    }

    public String getExtendedPriceAsCurrency() {
        return NumberFormat.getCurrencyInstance().format(getExtendedPrice());
    }

}