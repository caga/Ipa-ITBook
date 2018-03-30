package com.nealford.art.references;

public class CanonicalIntegers {
    public static void main(String[] args) {
        new CanonicalIntegers();
    }

    public CanonicalIntegers() {
        Tester t = new Tester();
        t.doIt(IntegerHelper.ONE);
        t.doIt(IntegerHelper.TWO);
        t.doIt(IntegerHelper.FIVE);

        CanonicalInt ci = CanonicalInt.getInstance();
        System.out.println("Here is canonical int = 3:" +
                           ci.getCanonicalInteger(3));
        System.out.println("Here is canonical int = 103:" +
                   ci.getCanonicalInteger(103));


    }
}

class Tester {
    public void doIt(Integer i) {
        if (i == IntegerHelper.ONE) {
            doSomething();
        } else if (i == IntegerHelper.TWO) {
            doSomethingElse();
        } else
            handleDefaultCase();
    }

    private void doSomething() {
        System.out.println("Handling integer 1");
    }

    private void doSomethingElse() {
        System.out.println("Handling integer 2");
    }

    private void handleDefaultCase() {
        System.out.println("Handling all others");
    }

}
