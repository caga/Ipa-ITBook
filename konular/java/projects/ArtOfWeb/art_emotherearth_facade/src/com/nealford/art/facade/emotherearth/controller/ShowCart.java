package com.nealford.art.facade.emotherearth.controller;

import com.nealford.art.facade.emotherearth.boundary.ProductDb;
import com.nealford.art.facade.emotherearth.entity.CartItem;
import com.nealford.art.facade.emotherearth.entity.Product;
import com.nealford.art.facade.emotherearth.util.ShoppingCart;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.nealford.art.facade.emotherearth.util.BoundaryFacade;
import com.nealford.art.facade.emotherearth.util.AttributeConstants;

public class ShowCart extends HttpServlet implements AttributeConstants {

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
//        ProductDb productDb = BoundaryFacade.getProductBoundary(
//                session);

        BoundaryFacade facade = BoundaryFacade.getInstance();
        ProductDb productDb = (ProductDb) facade.borrowBoundary(session, ProductDb.class);
        CartItem cartItem = buildCartItem(request, productDb,
                Integer.parseInt(request.getParameter(ID)));
        facade.returnBoundaries(session, true);
        cart.addItem(cartItem);
        session.setAttribute(CART, cart);
        dispatcher = request.getRequestDispatcher("/ShowCart.jsp");
        dispatcher.forward(request, response);
    }

    private CartItem buildCartItem(HttpServletRequest request,
                                   ProductDb productDb,
                                   int id) throws
            NumberFormatException {
        id = Integer.parseInt(request.getParameter(ID));
        int quantity =
                Integer.parseInt(request.getParameter(QUANTITY));
        Product product = productDb.getProduct(id);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    private ShoppingCart getOrCreateShoppingCart(
            HttpSession session) {
        ShoppingCart cart =
                (ShoppingCart) session.getAttribute(CART);
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