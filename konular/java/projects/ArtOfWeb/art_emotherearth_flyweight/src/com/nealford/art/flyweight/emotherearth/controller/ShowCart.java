package com.nealford.art.flyweight.emotherearth.controller;

import com.nealford.art.flyweight.emotherearth.boundary.ProductDb;
import com.nealford.art.flyweight.emotherearth.entity.CartItem;
import com.nealford.art.flyweight.emotherearth.entity.Product;
import com.nealford.art.flyweight.emotherearth.util.ShoppingCart;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Iterator;

public class ShowCart extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession session =
                redirectIfSessionNotPresent(request, response,
                dispatcher);
        ShoppingCart cart = getOrCreateShoppingCart(session);
        CartItem cartItem = buildCartItem(request,
                Integer.parseInt(request.getParameter("id")),
                getServletContext());
        cart.addItem(cartItem);
        session.setAttribute("cart", cart);
        dispatcher = request.getRequestDispatcher("/ShowCart.jsp");
        dispatcher.forward(request, response);
    }

    private CartItem buildCartItem(HttpServletRequest request,
                                   int id,
                                   ServletContext context) throws
            NumberFormatException {
        id = Integer.parseInt(request.getParameter("id"));
        int quantity =
                Integer.parseInt(request.getParameter("quantity"));
        Product product = getProductReference(context, id);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    private ProductDb getProductBoundary(HttpSession session) {
        ProductDb productDb = (
                ProductDb) session.getAttribute("productList");
        return productDb;
    }

    private Product getProductReference(ServletContext context,
                                        int id) {
        List productList = (List) context.getAttribute("products");
        Iterator it = productList.iterator();
        Product foundProduct = null;
        while (it.hasNext()) {
            Product p = (Product) it.next();
            if (p.getId() == id) {
                foundProduct = p;
                break;
            }
        }
        return foundProduct;
    }

    private ShoppingCart getOrCreateShoppingCart(
            HttpSession session) {
        ShoppingCart cart =
                (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
        }
        return cart;
    }

    private HttpSession redirectIfSessionNotPresent(
            HttpServletRequest request,
            HttpServletResponse response,
            RequestDispatcher dispatcher) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            dispatcher = request.getRequestDispatcher("/welcome");
            dispatcher.forward(request, response);
            return null;
        }
        return session;
    }
}