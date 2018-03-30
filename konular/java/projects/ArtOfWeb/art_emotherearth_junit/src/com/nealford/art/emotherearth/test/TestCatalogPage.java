package com.nealford.art.emotherearth.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class TestCatalogPage extends BaseWebTestCase {
    private static String LOG_DIR = "c:/temp/emotherearth/";

    public TestCatalogPage(String name) {
        super(name);
    }

    public void setUp() throws java.lang.Exception {
        super.setUp();
        File outputDir = new File(LOG_DIR);
        if (!outputDir.exists())
            outputDir.mkdir();
    }

    public void testCatalog() {
        beginAt("/welcome");
        beginAt("/catalog?user=Homer");
        assertTablePresent("catalogTable");
        assertTextInTable("catalogTable",
                new String[] {"ID", "NAME", "PRICE", "Buy"});
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(
                    "c:/temp/emotherearth/catalogText.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        dumpTable("catalogTable", ps);
    }
}