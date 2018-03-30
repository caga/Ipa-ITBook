package com.nealford.art.facade.emotherearth.util;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.nealford.art.facade.emotherearth.boundary.BoundaryBase;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class BoundaryFacade implements AttributeConstants {
    private static BoundaryFacade singleton;
    private List borrowedObjects = null;

//    public static ProductDb getProductBoundary(HttpSession session) {
//        ServletContext sc = session.getServletContext();
//
//        boolean cacheInBoundaryInSession =
//                Boolean.valueOf(sc.getInitParameter(
//                CACHE_BOUNDARY_IN_SESSION)).
//                booleanValue();
//        ProductDb productDb = null;
//        if (cacheInBoundaryInSession)
//            productDb = (ProductDb) session.getAttribute(
//                    PRODUCT_BOUNDARY);
//
//        if (productDb == null) {
//            GenericKeyedObjectPool boundaryPool =
//                    (GenericKeyedObjectPool) sc.getAttribute(
//                    BOUNDARY_POOL);
//            try {
//                productDb = (ProductDb)
//                            boundaryPool.borrowObject(ProductDb.class);
//            } catch (Throwable x) {
//                System.out.println("Pool exception");
//                sc.log("Object pool exception", x);
//            }
//            productDb.setDBPool((DBPool) sc.getAttribute(DB_POOL));
//
//            if (cacheInBoundaryInSession)
//                session.setAttribute(PRODUCT_BOUNDARY, productDb);
//        }
//        return productDb;
//    }

    private BoundaryFacade() {
        borrowedObjects = new ArrayList(5);
    }

    public static BoundaryFacade getInstance() {
        if (singleton == null)
            singleton = new BoundaryFacade();
        return singleton;

    }

    public void initiaizeBoundaryPool(ServletContext context) {
        GenericKeyedObjectPool boundaryPool =
                createBoundaryPool(context);
        context.setAttribute(BOUNDARY_POOL, boundaryPool);

    }

    private GenericKeyedObjectPool.Config getPoolConfiguration(
            ServletContext context) {
        GenericKeyedObjectPool.Config conf =
                new GenericKeyedObjectPool.Config();
        conf.maxActive = Integer.parseInt(context.getInitParameter(
                POOL_MAX_ACTIVE));
        conf.whenExhaustedAction = Byte.parseByte(context.
                getInitParameter(POOL_WHEN_EXHAUSTED));
        return conf;
    }

    private GenericKeyedObjectPool createBoundaryPool(
            ServletContext context) {
        GenericKeyedObjectPool pool = null;
        try {
            pool = new GenericKeyedObjectPool(
                    new KeyedBoundaryPoolFactory());
            pool.setConfig(getPoolConfiguration(context));
        } catch (Throwable x) {
            System.out.println("Pool creation exception: " +
                               x.getMessage());
            x.printStackTrace();
        }
        return pool;
    }


    public BoundaryBase borrowBoundary(HttpSession session,
                                       Class boundaryClass) {
        ServletContext sc = session.getServletContext();
        boolean cacheInBoundaryInSession =
                Boolean.valueOf(sc.getInitParameter(
                CACHE_BOUNDARY_IN_SESSION)).
                booleanValue();

        BoundaryBase boundary = null;
        if (cacheInBoundaryInSession)
            boundary = (BoundaryBase) session.getAttribute(
                    boundaryClass.getName());
        if (boundary == null) {
            try {
                GenericKeyedObjectPool boundaryPool =
                        (GenericKeyedObjectPool) sc.getAttribute(
                        BOUNDARY_POOL);

                boundary = (BoundaryBase) boundaryPool.
                           borrowObject(boundaryClass);

                if (cacheInBoundaryInSession &&
                    boundary instanceof Cacheable)
                    session.setAttribute(boundaryClass.getName(),
                            boundary);
            } catch (Exception x) {
                session.getServletContext().log("Pool error", x);
            }
            boundary.setDBPool((DBPool) sc.getAttribute(DB_POOL));
            borrowedObjects.add(boundary);
        }
        return boundary;
    }

//
//    public static OrderDb borrowOrderBoundary(HttpSession session) {
//        ServletContext sc = session.getServletContext();
//        OrderDb orderDb = null;
//        try {
//            GenericKeyedObjectPool boundaryPool =
//                    (GenericKeyedObjectPool) sc.getAttribute(
//                    BOUNDARY_POOL);
//
//            orderDb = (OrderDb) boundaryPool.borrowObject(OrderDb.class);
//        } catch (Exception x) {
//            System.out.println("Pool exception");
//            session.getServletContext().log("Pool exception", x);
//        }
//        orderDb.setDBPool((DBPool) sc.getAttribute(DB_POOL));
//        return orderDb;
//    }
//

    public void returnBoundaries(HttpSession session,
                                 boolean preserveCachedBoundaries) {
        GenericKeyedObjectPool boundaryPool =
            (GenericKeyedObjectPool) session.getServletContext().
            getAttribute(BOUNDARY_POOL);
        boolean cacheInBoundaryInSession =
            Boolean.valueOf(session.getServletContext().
            getInitParameter(CACHE_BOUNDARY_IN_SESSION)).
            booleanValue();
        Iterator borrowedObject = borrowedObjects.iterator();
        while (borrowedObject.hasNext()) {
            Object o = borrowedObject.next();
            if (o instanceof BoundaryBase)
                if (cacheInBoundaryInSession &&
                        preserveCachedBoundaries &&
                        o instanceof Cacheable)
                    break;
                else {
                    try {
                        boundaryPool.returnObject(o.getClass(), o);
                    } catch (Exception x) {
                        session.getServletContext().log(
                                "Pool return exception: " +
                                x.getMessage());
                    } finally {
                        borrowedObject.remove();
                    }
                }
        }
    }

//
//    public void returnProductBoundary(ProductDb productDb) {
//        try {
//            boundaryPool.returnObject(ProductDb.class,
//                    productDb);
//        } catch (Throwable x) {
//            session.getServletContext().log(
//                    "Pool return exception: " +
//                    x.getMessage());
//        }
//    }
//
//    public void conditionallyReturnProductBoundary(
//            ProductDb productDb) {
//        ServletContext sc = session.getServletContext();
//        boolean cacheInBoundaryInSession =
//                Boolean.valueOf(sc.getInitParameter(
//                CACHE_BOUNDARY_IN_SESSION)).
//                booleanValue();
//
//        if (! cacheInBoundaryInSession && productDb != null) {
//            GenericKeyedObjectPool boundaryPool =
//                    (GenericKeyedObjectPool)
//                    session.getServletContext().getAttribute(
//                    BOUNDARY_POOL);
//            returnProductBoundary(productDb);
//        }
//    }
//
//    public  void returnBoundaries(HttpSession session,
//                                        OrderDb orderDb) {
//        ServletContext sc = session.getServletContext();
//        boolean cacheInBoundaryInSession =
//                Boolean.valueOf(sc.getInitParameter(
//                CACHE_BOUNDARY_IN_SESSION)).
//                booleanValue();
//
//        ProductDb productDb =
//                (ProductDb) session.getAttribute(PRODUCT_BOUNDARY);
//        GenericKeyedObjectPool boundaryPool =
//                (GenericKeyedObjectPool) sc.getAttribute(
//                BOUNDARY_POOL);
//
//        try {
//            if (productDb != null && !cacheInBoundaryInSession)
//                boundaryPool.returnObject(ProductDb.class,
//                        productDb);
//            if (orderDb != null)
//                boundaryPool.returnObject(OrderDb.class,
//                        orderDb);
//        } catch (Exception x) {
//            sc.log("Pool exception", x);
//        }
//    }
}