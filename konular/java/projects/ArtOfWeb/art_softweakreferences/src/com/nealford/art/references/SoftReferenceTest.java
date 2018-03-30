package com.nealford.art.references;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SoftReferenceTest {


    public SoftReferenceTest() {
        List softList = new ArrayList(5);

        StringBuffer s1 = new StringBuffer("Now is the time");

        softList.add(new SoftReference(s1));
        softList.add(new SoftReference(
                new StringBuffer("for all good men")));
        softList.add(new SoftReference(
                new StringBuffer("to come to the aid")));

        s1 = null;

        for (int i = 0; i < softList.size(); i++) {
            StringBuffer s = (StringBuffer)
                     ((SoftReference) softList.get(i)).get();
            System.out.print("List item # " + i + '\t');
            if (s == null)
                System.out.println(" has been reclaimed");
            else
                System.out.println(s);
        }

    }

    public static void main(String[] args) {
        new SoftReferenceTest();
    }
}