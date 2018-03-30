package com.nealford.art.emotherearth.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nealford.art.emotherearth.boundary.ProductDb;
import com.nealford.art.emotherearth.util.DBPool;
import com.nealford.art.emotherearth.util.IdComparator;
import com.nealford.art.emotherearth.util.NameComparator;
import com.nealford.art.emotherearth.util.PriceComparator;


public class Catalog extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {

        HttpSession session = request.getSession(true);
        ensureThatUserIsInSession(request, session);
        ProductDb productDb = getProductBoundary(session);
        int start = getStartingPage(request);
        int recsPerPage = Integer.parseInt(getServletConfig().
                                           getInitParameter(
                "recsPerPage"));
        int totalPagesToShow = calculateNumberOfPagesToShow(
                productDb.getProductList().size(), recsPerPage);
        String[] pageList =
                buildListOfPagesToShow(recsPerPage,
                                       totalPagesToShow);
        List outputList = productDb.getProductListSlice(start,
                recsPerPage);
        sortPagesForDisplay(request, productDb, outputList);

        bundleInformationForView(request, start, pageList,
                                 outputList);
        forwardToView(request, response);
    }

    private void forwardToView(HttpServletRequest request,
                               HttpServletResponse response) throws
            ServletException, IOException {
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

        if ((userInSession == null) && (userInRequest != null)) {
            session.setAttribute("user", userInRequest);
        }
    }

    private void sortPagesForDisplay(HttpServletRequest request,
                                     ProductDb productDb,
                                     List outputList) {
        productDb.sortList(request.getParameter("sort"),
                           outputList);
    }

    private String[] buildListOfPagesToShow(int recsPerPage,
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
            currentPage += recsPerPage;
        }
        return pageList;
    }

    private int getStartingPage(HttpServletRequest request) {
        String recStart = request.getParameter("start");
        int start = 0;

        if (recStart != null) {
            start = Integer.parseInt(recStart);
        }
        return start;
    }

    private int calculateNumberOfPagesToShow(int numInList,
                                             int recsPerPage) {
        int totalToShow = numInList / recsPerPage;

        if (numInList % recsPerPage != 0)
            ++totalToShow;

        return totalToShow;
    }

    private ProductDb getProductBoundary(HttpSession session) throws
            NumberFormatException {
        ProductDb products = (ProductDb) session.getAttribute(
                "productBoundary");

        if (products == null) {
            products = new ProductDb();
            products.setDbPool(
                    (DBPool) getServletContext().getAttribute(
                    "dbPool"));
            session.setAttribute("productList", products);
        }
        return products;
    }
}