package com.nealford.art.facade.emotherearth.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nealford.art.facade.emotherearth.boundary.OrderDb;
import com.nealford.art.facade.emotherearth.entity.Order;
import com.nealford.art.facade.emotherearth.util.BoundaryFacade;
import com.nealford.art.facade.emotherearth.util.ShoppingCart;
import com.nealford.art.facade.emotherearth.util.AttributeConstants;

public class Checkout extends HttpServlet implements
        AttributeConstants {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession session = redirectIfSessionNotPresent(
                request, response);
        String user = (String) session.getAttribute(USER);
        ShoppingCart sc =
                (ShoppingCart) session.getAttribute(CART);
        BoundaryFacade facade = BoundaryFacade.getInstance();
        OrderDb orderDb = (OrderDb) facade.borrowBoundary(session,
                OrderDb.class);
        Order order = createOrderFrom(request);
        validateOrder(request, response, order);
        addOrder(request, response, user, sc, orderDb, order);
        cleanUpUserResources(session);
        buildConfirmationViewProperties(request, user, order);
        forwardToConfirmation(request, response);
    }

    private void cleanUpUserResources(HttpSession session) {
        BoundaryFacade facade = BoundaryFacade.getInstance();
        facade.returnBoundaries(session, false);
        session.invalidate();
    }

    private void forwardToConfirmation(HttpServletRequest request,
                                       HttpServletResponse response) throws
            ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/CheckOutView.jsp");
        dispatcher.forward(request, response);
    }

    private void buildConfirmationViewProperties(HttpServletRequest
            request, String user, Order order) {
        request.setAttribute(USER, user);
        request.setAttribute(CONFIRMATION,
                             new Integer(order.getOrderKey()));
    }

    private void addOrder(HttpServletRequest request,
                          HttpServletResponse response, String user,
                          ShoppingCart sc, OrderDb orderDb,
                          Order order) throws ServletException,
            IOException {
        try {
            orderDb.addOrder(sc, user, order);
        } catch (SQLException sqlx) {
            request.setAttribute(
                    "javax.servlet.jsp.jspException", sqlx);
            RequestDispatcher dispatcher = request.
                    getRequestDispatcher(
                    "/SQLErrorPage.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private void validateOrder(HttpServletRequest request,
                               HttpServletResponse response,
                               Order order) throws ServletException,
            IOException {
        List errorList = order.validate();
        if (!errorList.isEmpty()) {
            request.setAttribute(ERROR_LIST, errorList);
            RequestDispatcher dispatcher = request.
                    getRequestDispatcher("/ShowCart");
            dispatcher.forward(request, response);
            return;
        }
    }

    private Order createOrderFrom(HttpServletRequest request) {
        Order order = new Order();
        order.setCcNum(request.getParameter("ccNum"));
        order.setCcExp(request.getParameter("ccExp"));
        order.setCcType(request.getParameter("ccType"));
        return order;
    }

    private HttpSession redirectIfSessionNotPresent(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RequestDispatcher dispatcher = request.
                    getRequestDispatcher("/welcome");
            dispatcher.forward(request, response);
        }
        return session;
    }
}
