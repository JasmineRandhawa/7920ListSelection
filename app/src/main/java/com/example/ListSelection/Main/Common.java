package com.example.ListSelection.Main;

import java.math.BigDecimal;

// common reusable functions defined here
public class Common {

    // control number of successful attempts needed for a trial
    private static final int TotalSuccessfulAttemptsNeededPerTrial = 10;

    public static int getTotalSuccessfulAttemptsNeededPerTrial() {
        return TotalSuccessfulAttemptsNeededPerTrial;
    }

    // called to randomly generate date from previous two decades
    public static String GenerateRandomMiddleTopListOption(String currentListOption) {
        String[] list = new String[]{"Emo", "Delta", "Dalmeny", "Duchess",
                "Elton", "Cedar", "Chase", "Chester",
                "Erin", "Essa"};
        if (currentListOption.equals("")) {
            return list[0];
        } else {
            int index = 0;
            for (int i = 0; i <= list.length - 1; i++) {
                if (list[i].equals(currentListOption)) {
                    index = i;
                    break;
                }
            }
            return list[index + 1];
        }
    }

    // called to randomly generate date from previous four decades
    public static String GenerateRandomMiddleListOption(String currentListOption) {
        String[] list = new String[]{"Lamont", "Gordon", "Kerrobert", "Lakeview",
                    "Kent", "Lappe", "Gimli", "Kenora",
                "Grey", "Greenstone"};
        if (currentListOption.equals("")) {
            return list[0];
        } else {
            int index = 0;
            for (int i = 0; i <= list.length - 1; i++) {
                if (list[i].equals(currentListOption)) {
                    index = i;
                    break;
                }
            }
            return list[index + 1];
        }
    }

    // called to randomly generate date from previous six decades
    public static String GenerateRandomMiddleDownListOption(String currentListOption) {
        String[] list = new String[]{"Peel", "Redwater", "Pinehouse", "Oakview",
                "RedDeer", "Outlook", "Riverdale", "Perth",
                "Oliver", "Raymond"};
        if (currentListOption.equals("")) {
            return list[0];
        } else {
            int index = 0;
            for (int i = 0; i <= list.length - 1; i++) {
                if (list[i].equals(currentListOption)) {
                    index = i;
                    break;
                }
            }
            return list[index + 1];
        }
    }

    //round up float values to specific number of decimal places
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
