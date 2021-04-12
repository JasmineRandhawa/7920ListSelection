package com.example.ListSelection.Main;

import java.math.BigDecimal;

// common reusable functions defined here
public class Common {

    // control number of successful attempts needed for a trial
    private static final int TotalSuccessfulAttemptsNeededPerTrial = 2;

    public static int getTotalSuccessfulAttemptsNeededPerTrial() {
        return TotalSuccessfulAttemptsNeededPerTrial;
    }

    // called to randomly generate date from previous two decades
    public static String GenerateRandomMiddleTopListOption(String currentListOption) {
        //int r = (int) (Common.RandInt());
        // String name = new String[]{"13","24","12","34", "43", "21", "42","31"}[r];
        // return name;
        return "Alfred";
    }

    // called to randomly generate date from previous four decades
    public static String GenerateRandomMiddleListOption(String currentListOption) {
        //int r = (int) (Common.RandInt());
        // String name = new String[]{"13","24","12","34", "43", "21", "42","31"}[r];
        // return name;
        return "Alfred";
    }

    // called to randomly generate date from previous six decades
    public static String GenerateRandomMiddleDownListOption(String currentListOption) {
        //int r = (int) (Common.RandInt());
        // String name = new String[]{"13","24","12","34", "43", "21", "42","31"}[r];
        // return name;
        return "Alfred";
    }

    //round up float values to specific number of decimal places
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
