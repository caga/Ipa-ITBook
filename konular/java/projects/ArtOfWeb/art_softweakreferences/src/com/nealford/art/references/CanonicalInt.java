package com.nealford.art.references;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CanonicalInt {
    private static CanonicalInt internalReference = null;
    private static final int MAX = 100;
    private List intList;

    private CanonicalInt() {
        intList = new ArrayList(MAX);
        buildIntsToMax();
    }

    public static CanonicalInt getInstance() {
        if (internalReference == null)
            internalReference = new CanonicalInt();
        return internalReference;
    }

    private void buildIntsToMax() {
        for (int i = 0; i < MAX; i++ )
            intList.add(new WeakReference(new Integer(i)));
    }

    public synchronized Integer getCanonicalInteger(int i) {
        //-- only handle integers within range
        if (i > intList.size())
            return new Integer(i);
        Integer canonicalInt = null;
        WeakReference ref = (WeakReference) intList.get(i);
        if (ref == null ||
            ((canonicalInt = (Integer) ref.get()) == null)) {
            canonicalInt = new Integer(i);
            intList.set(i, new WeakReference(canonicalInt));
        }
        return canonicalInt;
    }
}