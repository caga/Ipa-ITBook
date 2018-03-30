package com.nealford.art.cachingpool.emotherearth.servlet;

import com.nealford.art.cachingpool.emotherearth.boundary.ProductDb;
import com.nealford.art.cachingpool.emotherearth.util.DBPool;
import com.nealford.art.cachingpool.emotherearth.util.IdComparator;
import com.nealford.art.cachingpool.emotherearth.util.NameComparator;
import com.nealford.art.cachingpool.emotherearth.util.PriceComparator;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;


public class Catalog extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
               throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        ProductDb products = getProductBoundary(session);
        int start = getStartingPage(request);
        int totalPagesToShow =
                calculateNumberOfPagesToShow(products);
        String[] pageList =
                buildListOfPagesToShow(products, totalPagesToShow);
        List outputList = products.getProductListSlice(start);
        sortPagesForDisplay(request, outputList);
        ensureThatUserIsInSession(request, session);
        bundleInformationForView(request, start, pageList,
                                 outputList);
        forwardToView(request, response);
    }

    private void forwardToView(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(
                                               "/CatalogView.jsp");
        dispatcher.forward(request, response);
    }

    private void bundleInformationForView(
            HttpServletRequest request, int start,
            String[] pageList, List outputList) {
        request.setAttribute("start", new Integer(start));
        request.setAttribute("pageList", pageList);
        request.setAttribute("outputList", outputList);
    }

    private void ensureThatUserIsInSession(
            HttpServletRequest request, HttpSession session) {
        String userInSession =
                (String) session.getAttribute("user");
        String userInRequest = request.getParameter("user");

        if ((userInSession == null) &&  (userInRequest != null))
            session.setAttribute("user", userInRequest);
    }

    private void sortPagesForDisplay(HttpServletRequest request,
                                     List outputList) {
        String sortField = request.getParameter("sort");

        if (sortField != null) {

            //-- sort by column passed as sort
            Comparator c = new IdComparator();

            if (sortField.equalsIgnoreCase("price"))
                c = new PriceComparator();
            else if (sortField.equalsIgnoreCase("name"))
                c = new NameComparator();

            Collections.sort(outputList, c);
        }
    }

    private String[] buildListOfPagesToShow(ProductDb products,
            int totalPagesToShow) {
        String[] pageList = new String[totalPagesToShow];
        StringBuffer work = new StringBuffer(20);
        int currentPage = 0;

        for (int i = 0; i < totalPagesToShow; i++) {
            work.setLength(0);
            work.append("<a href='catalog?start=").append(
                    currentPage).append("'>").append(i + 1).append(
                    "</a>&nbsp;");
            pageList[i] = work.toString();
            currentPage += getPageSize(products);
        }
        return pageList;
    }

    private int getStartingPage(HttpServletRequest request)
            throws NumberFormatException {
        String recStart = request.getParameter("start");
        int start = 0;

        if (recStart != null)
            start = Integer.parseInt(recStart);
        return start;
    }

    private int calculateNumberOfPagesToShow(ProductDb products) {
        int total = products.getProductList().size();
        int pageSize = getPageSize(products);
        int totalToShow = total / pageSize;

        if (total % pageSize != 0)
            ++totalToShow;
        return totalToShow;
    }

    private int getPageSize(ProductDb products) {
        int pageSize = products.getRecordsPerPage();
        return pageSize;
    }

    private ProductDb getProductBoundary(HttpSession session) {
        ProductDb products = (ProductDb)session.getAttribute(
                                     "productBoundary");

        if (products == null) {
            GenericKeyedObjectPool boundaryPool =
                    (GenericKeyedObjectPool) getServletContext().
                    getAttribute("boundaryPool");
            try {
                products = (ProductDb)
                        boundaryPool.borrowObject(ProductDb.class);
            }
            catch (Throwable x) {
                System.out.println("Pool exception");
                getServletContext().log("Object pool exception", x);
            }
            products.setDbPool(
                    (DBPool)getServletContext().getAttribute(
                            "dbPool"));

            session.setAttribute("productList", products);

            int recsPerPage = Integer.parseInt(getServletConfig().
                    getInitParameter("recsPerPage"));
            products.setRecordsPerPage(recsPerPage);
        }
        return products;
    }
}