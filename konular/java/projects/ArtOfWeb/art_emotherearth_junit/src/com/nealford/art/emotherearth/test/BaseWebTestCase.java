package com.nealford.art.emotherearth.test;

import net.sourceforge.jwebunit.WebTestCase;

public class BaseWebTestCase extends WebTestCase {

    public BaseWebTestCase(String name) {
        super(name);
    }

    public void setUp() throws java.lang.Exception {
        super.setUp();
        getTestContext().setBaseUrl(
                "http://localhost:8080/emotherearth");
    }
}