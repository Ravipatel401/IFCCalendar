package com.approidtech.ifccalendar;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IFCMethods {

    String date="01/02/2022";
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

    private String GorDate;
    private String IFCDate;
    private static final String[] MONTHS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private static final String[] convertMonth = {"11", "12", "13", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public void GorIFC() throws ParseException {
        String convertedMonth;
        Date d=dateFormat.parse(date);
        String month = (String) DateFormat.format("MM", d);
        int monthNum = Integer.parseInt(month);
        Log.e(">Month", month);
        int[] cfs = {23, 23, 26, 28, 0, 2, 5, 7, 10, 13, 15, 18, 20};
        String day = (String) DateFormat.format("dd",d);
        int dayNum = Integer.parseInt(day);
        Log.e(">Day", String.valueOf(dayNum));
        Log.e(">Month", String.valueOf(monthNum));
        dayNum +=cfs[monthNum];
        if (dayNum > 28 && monthNum != 13){
            dayNum -= 28;
            month = MONTHS[monthNum];
        }
     /*   if(Integer.parseInt(month)> 10){
             convertedMonth = convertMonth[monthNum];
        }else {
             convertedMonth = convertMonth[monthNum-1];
        }*/
        convertedMonth = convertMonth[monthNum-1];
        Log.e(">Day and Month", dayNum +" "+ month +" New Month " + convertedMonth);

    }

}
