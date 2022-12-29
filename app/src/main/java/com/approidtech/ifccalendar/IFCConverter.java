package com.approidtech.ifccalendar;

import android.util.Log;

public class IFCConverter {

    private String gregDate ="March 30";//a gregorian date
    private String IFCdate;//an International fixed calendar date
    private static final String[] MONTHS = {"April", "May", "June", "July", "August", "September", "October", "November", "December","January", "February", "March","Midi"};//creates an constant array of months

    //sets gregDate
    public void setGregDate(String gD) {
        if (gD.contains("  ") && !gD.startsWith(" ")) { //if the spaces won't //drive later code wacko
            gregDate = "11 November 2022";
        }
    }

    //gets IFCdate
    public String getIFCdate() {
        GregIFC();
        return IFCdate;
    }

    public void GregIFC() {
        String month = gregDate.substring(0, gregDate.indexOf(' '));//separates the Month part of the date
        Log.e("Month", month);
        int monthNum = monthNum(month);//sets a month # equal to the Number of the //month (0 fo Jan., 1 for Feb., etc.)
        //int[] cfs = {0, 3, 3, 6, 8, 11, 13, 0, -12, -9, -7, -4, -2};//sets the conversion factors
        int[] cfs = {0, 2, 5, 7, 10, 13, 15, 18, 20, 23, 26, 26, 28};

        String dayStr = gregDate.substring(gregDate.indexOf(' ') + 1);//separates the day of the month
        Log.e("Day", dayStr);
        int day = Integer.parseInt(dayStr);//parses the day string into a number
        day += cfs[monthNum];//sets IFC day of month
        if (day > 28 && monthNum != 12) {//if IFC date is greater than 28 and it //isn't the 29 day month
            day -= 28;
            month = MONTHS[monthNum + 1];//subtract 28 and go to the next month
        }
        if (day < 1) {//if IFC date is less than 1
            day += 28;
            month = MONTHS[monthNum - 1];//add 28 and go to previous month
        }
        IFCdate = month + " " + day;//sets IFCdate equal to final month and day
        if (gregDate.equals("March 31")) {//because in the International Fixed Calendar, there's no Dec. //29
            IFCdate = "Extra day";
        }

        Log.e(">Print Date: ",IFCdate );
    }

    public int monthNum(String month) {
        String subMonth = month.substring(0, 3);//so abbreviations will work
        int monthN;
        for (monthN = 0; monthN < MONTHS.length; monthN++) {//don't want to have the index be out of bounds
            if (subMonth.equals(MONTHS[monthN].substring(0, 3))) {//if month equals current month
                break;//leave monthN where it is
            }
        }
        return monthN;
    }
}
