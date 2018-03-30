package com.nealford.art.parameterizedcommands;

import java.util.*;
import java.io.*;

/**
 * Helper class to generate an initial <code>mappings.properties
 * </code> file.
 *
 * @author Neal Ford
 */
public class GenerateInitialMappings {
    private static final String PKG =
            "com.nealford.pragmatic.parameterizedrequests.";

    public GenerateInitialMappings() {
        System.out.print("Generating initial mappings file...");
        Properties p = new Properties();
        p.put("listing", PKG + "ListingAction");
        p.put("formEntry", PKG + "FormEntry");
        p.put("saveAction", PKG + "SaveAction");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("mappings.properties");
            p.store(fos, "Initial generation");
        } catch (IOException iox) {
            iox.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException ignored) {
            } //catch
        } //catch/finally
        System.out.println("Done.");
    }

    public static void main(String[] args) {
        new GenerateInitialMappings();
    }

}