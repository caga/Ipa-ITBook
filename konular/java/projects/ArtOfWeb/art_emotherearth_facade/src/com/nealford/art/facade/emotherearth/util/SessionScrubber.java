package com.nealford.art.facade.emotherearth.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.nealford.art.facade.emotherearth.boundary.ProductDb;

public class SessionScrubber implements
        HttpSessionAttributeListener, AttributeConstants {

    public void attributeAdded(HttpSessionBindingEvent se) {
    }

//    public void attributeRemoved(HttpSessionBindingEvent se) {
//        ServletContext sc = se.getSession().getServletContext();
//        if (se.getName().equals(PRODUCT_BOUNDARY))
//            BoundaryFacade.getInstance().
//                   returnProductBoundary((ProductDb) se.getValue());
//    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        BoundaryFacade facade = BoundaryFacade.getInstance();
        facade.returnBoundaries(se.getSession(), false);
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    }

}
