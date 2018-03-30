package com.nealford.art.cachingpool.emotherearth.servlet;

import com.nealford.art.cachingpool.emotherearth.boundary.ProductDb;
import com.nealford.art.cachingpool.emotherearth.entity.CartItem;
import com.nealford.art.cachingpool.emotherearth.entity.Product;
import com.nealford.art.cachingpool.emotherearth.util.ShoppingCart;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowCart extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession session =
                redirectIfSessionNotPresent(request, response,
                dispatcher);
        ShoppingCart cart = getOrCreateShoppingCart(session);
        ProductDb productDb = getProductBoundary(session);

        CartItem cartItem = buildCartItem(request, productDb,
                Integer.parseInt(request.getParameter("id")));
        cart.addItem(cartItem);
        session.setAttribute("cart", cart);
        dispatcher = request.getRequestDispatcher("/ShowCart.jsp");
        dispatcher.forward(request, response);
    }

    private CartItem buildCartItem(HttpServletRequest request,
                                   ProductDb productDb,
                                   int id)
            throws NumberFormatException {
        id = Integer.parseInt(request.getParameter("id"));
        int quantity =
                Integer.parseInt(request.getParameter("quantity"));
        Product product = productDb.getProduct(id);
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
                RequestDispatcher dispatcher)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            dispatcher = request.getRequestDispatcher("/welcome");
            dispatcher.forward(request, response);
            return null;
        }
        return session;
    }
}