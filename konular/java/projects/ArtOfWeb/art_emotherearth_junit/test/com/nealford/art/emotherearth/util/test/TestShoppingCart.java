package com.nealford.art.emotherearth.util.test;

import com.nealford.art.emotherearth.entity.CartItem;
import com.nealford.art.emotherearth.entity.Product;
import com.nealford.art.emotherearth.util.ShoppingCart;
import junit.framework.TestCase;

public class TestShoppingCart extends TestCase {
    protected ShoppingCart shoppingCart = null;
    static int productNum = 0;
    protected CartItem[] items;

    public TestShoppingCart(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        shoppingCart = new ShoppingCart();
        items = new CartItem[4];
        for (int i = 0; i < items.length; i++) {
            items[i] = generateRandomCartItem();
            shoppingCart.addItem(items[i]);
        }
    }

    protected void tearDown() throws Exception {
        shoppingCart = null;
        items = null;
        super.tearDown();
    }

    public void testGetCartTotal() {
        double expectedReturn = 0.0;
        for (int i = 0; i < items.length; i++) {
            expectedReturn += items[i].getExtendedPrice();
        }

        double actualReturn = shoppingCart.getCartTotal();
        assertEquals("cart total", expectedReturn, actualReturn,
                     0.01);
    }

    private CartItem generateRandomCartItem() {
        CartItem c = new CartItem();
        c.setProduct(getProduct());
        c.setQuantity((int) Math.round(Math.random() * 100));
        return c;
    }

    private Product getProduct() {
        Product p = new Product();
        p.setName("Test Product " + ++productNum);
        p.setPrice(Math.random() * 1000);
        return p;
    }
}
