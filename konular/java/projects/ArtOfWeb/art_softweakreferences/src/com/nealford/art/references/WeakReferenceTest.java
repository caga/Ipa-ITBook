package com.nealford.art.references;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakReferenceTest {

    public WeakReferenceTest() {
        StringBuffer s1 = new StringBuffer("Now is the time");

        Map weakMap = new WeakHashMap(5);
        weakMap.put("No. 1", s1);
        weakMap.put("No. 2", new StringBuffer("for all good men"));
        weakMap.put("No. 3", new StringBuffer("to come to the aid"));

        s1 = null;

        Set keySet = weakMap.keySet();
        Iterator keys =  keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.print("Key = " + key + '\t');
            Object o = weakMap.get(key);
            if (o == null)
                System.out.println("object has been reclaimed");
            else
                System.out.println(o);
        }

    }

    public static void main(String[] args) {
        new WeakReferenceTest();
    }
}