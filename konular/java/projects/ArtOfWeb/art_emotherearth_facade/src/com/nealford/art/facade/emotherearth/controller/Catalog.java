package com.nealford.art.facade.emotherearth.controller;

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

import com.nealford.art.facade.emotherearth.boundary.ProductDb;
import com.nealford.art.facade.emotherearth.util.AttributeConstants;
import com.nealford.art.facade.emotherearth.util.BoundaryFacade;
import com.nealford.art.facade.emotherearth.util.IdComparator;
import com.nealford.art.facade.emotherearth.util.NameComparator;
import com.nealford.art.facade.emotherearth.util.PriceComparator;


public class Catalog extends HttpServlet implements AttributeConstants{
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws
            ServletException, IOException {

        HttpSession session = request.getSession(true);
        BoundaryFacade facade = BoundaryFacade.getInstance();
        ProductDb products =
                (ProductDb) facade.borrowBoundary(session,
                ProductDb.class);
        int start = getStartingPage(request);
        int recsPerPage = Integer.parseInt(getServletConfig().
                getInitParameter(RECS_PER_PAGE));
        int totalPagesToShow =
                calculateNumberOfPagesToShow(products, recsPerPage);
        String[] pageList = buildListOfPagesToShow(totalPagesToShow,
                                       recsPerPage);
        facade.returnBoundaries(session, true);
        List outputList = products.getProductListSlice(start,
                recsPerPage);
        sortPagesForDisplay(request, outputList);
        ensureThatUserIsInSession(request, session);
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
        request.setAttribute(START_PAGE, new Integer(start));
        request.setAttribute(PAGE_LIST, pageList);
        request.setAttribute(OUTPUT_LIST, outputList);
    }

    private void ensureThatUserIsInSession(
            HttpServletRequest request, HttpSession session) {
        String userInSession =
                (String) session.getAttribute(USER);
        String userInRequest = request.getParameter(USER);

        if ((userInSession == null) && (userInRequest != null))
            session.setAttribute(USER, userInRequest);
    }

    private void sortPagesForDisplay(HttpServletRequest request,
                                     List outputList) {
        String sortField = request.getParameter(SORT_FLAG);

        if (sortField != null) {
            Comparator c = new IdComparator();

            if (sortField.equalsIgnoreCase(SORT_BY_PRICE))
                c = new PriceComparator();
            else if (sortField.equalsIgnoreCase(SORT_BY_NAME))
                c = new NameComparator();

            Collections.sort(outputList, c);
        }
    }

    private String[] buildListOfPagesToShow(int totalPagesToShow,
            int recsPerPage) {
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

    private int getStartingPage(HttpServletRequest request) throws
            NumberFormatException {
        String recStart = request.getParameter(START_PAGE);
        int start = 0;

        if (recStart != null)
            start = Integer.parseInt(recStart);
        return start;
    }

    private int calculateNumberOfPagesToShow(ProductDb products,
            int recsPerPage) {
        int total = products.getProductList().size();
        int pageSize = recsPerPage;
        int totalToShow = total / pageSize;

        if (total % pageSize != 0)
            ++
                totalToShow;
        return totalToShow;
    }

}