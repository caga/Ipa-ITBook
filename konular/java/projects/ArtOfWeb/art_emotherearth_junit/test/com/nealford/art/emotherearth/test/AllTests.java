package com.nealford.art.emotherearth.test;

import junit.framework.*;

public class AllTests extends TestCase {

    public AllTests(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(com.nealford.art.emotherearth.boundary.
                           test.TestOrderDb.class);
        suite.addTestSuite(com.nealford.art.emotherearth.util.test.
                           TestShoppingCart.class);
        return suite;
    }
}
