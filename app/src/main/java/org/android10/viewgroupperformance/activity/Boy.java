package org.android10.viewgroupperformance.activity;

public class Boy {

    public static void fuckYou() {
        myFunc();
    }

    private static void myFunc() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
