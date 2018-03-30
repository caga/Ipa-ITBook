package com.nealford.art.emotherearth.test;

public class TestLogonPage extends BaseWebTestCase {

    public TestLogonPage(String name) {
        super(name);
    }

    public void testIntro() {
        beginAt("/welcome");
    }

    public void testLogonElements() {
        beginAt("/welcome");
        assertFormPresent("welcomeform");
        assertFormElementPresent("user");
        assertFormElementPresent("gotocatalog");
    }

    public void testForwardToCatalog() {
        beginAt("/welcome");
        setFormElement("user", "Homer");
        submit();
    }
}