package com.nealford.art.cachingpool.emotherearth.servlet;

import com.nealford.art.cachingpool.emotherearth.boundary.OrderDb;
import com.nealford.art.cachingpool.emotherearth.boundary.ProductDb;
import com.nealford.art.cachingpool.emotherearth.entity.Order;
import com.nealford.art.cachingpool.emotherearth.util.DBPool;
import com.nealford.art.cachingpool.emotherearth.util.ShoppingCart;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

public class Checkout extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession session = redirectIfSessionNotPresent(
                request, response, dispatcher);
        String user = (String) session.getAttribute("user");
        ShoppingCart sc =
                (ShoppingCart) session.getAttribute("cart");
        DBPool dbPool =
                (DBPool) getServletContext().getAttribute("dbPool");
        GenericKeyedObjectPool boundaryPool =
            (GenericKeyedObjectPool) getServletContext().
            getAttribute("boundaryPool");
        OrderDb orderDb = getOrderBoundary(session, dbPool,
                boundaryPool);
        Order order = createOrderFrom(request);
        validateOrder(request, response, dispatcher, order);
        addOrder(request, response, dispatcher, user, sc, orderDb,
                 order);
        cleanUpUserResources(session, boundaryPool, orderDb);
        buildConfirmationViewProperties(request, user, order);
        forwardToConfirmation(request, response, dispatcher);
    }

    private void cleanUpUserResources(HttpSession session,
            GenericKeyedObjectPool boundaryPool, OrderDb orderDb) {
        returnBoundaryObjectsToPool(session, boundaryPool, orderDb);
        session.invalidate();
    }

    private void forwardToConfirmation(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher) throws ServletException, IOException {
        dispatcher = request.getRequestDispatcher("/CheckOutView.jsp");
        dispatcher.forward(request, response);
    }

    private void buildConfirmationViewProperties(HttpServletRequest request, String user, Order order) {
        request.setAttribute("user", user);
        request.setAttribute("confirmation", new Integer(order.getOrderKey()));
    }

    private void returnBoundaryObjectsToPool(HttpSession session,
            GenericKeyedObjectPool boundaryPool, OrderDb orderDb) {
        ProductDb productDb =
                (ProductDb) session.getAttribute("productList");
        try {
            if (productDb != null)
                boundaryPool.returnObject(ProductDb.class,
                        productDb);
            if (orderDb != null)
                boundaryPool.returnObject(OrderDb.class, orderDb);
        }
        catch (Exception x) {
            getServletContext().log("Pool exception", x);
        }
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher, String user, ShoppingCart sc, OrderDb orderDb, Order order) throws ServletException, IOException {
        try {
            orderDb.addOrder(sc, user, order);
        } catch (SQLException sqlx) {
            request.setAttribute(
                "javax.servlet.jsp.jspException", sqlx);
            dispatcher = request.getRequestDispatcher("/SQLErrorPage.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private void validateOrder(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher, Order order) throws ServletException, IOException {
        List errorList = order.validate();
        if (! errorList.isEmpty()) {
            request.setAttribute("errorList", errorList);
            dispatcher = request.getRequestDispatcher("/ShowCart");
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

    private OrderDb getOrderBoundary(HttpSession session, DBPool dbPool, GenericKeyedObjectPool boundaryPool) {
        OrderDb orderDb = (OrderDb) session. getAttribute("orderDb");
        if (orderDb == null) {
            try {
                orderDb = (OrderDb) boundaryPool.borrowObject(OrderDb.class);
            }
            catch (Exception x) {
                System.out.println("Pool exception");
                getServletContext().log("Pool exception", x);
            }
            orderDb.setDbPool(dbPool);
        }
        return orderDb;
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
