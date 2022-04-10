package com.example.sahneovevi.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public static String getDateFull(){
        DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss");
        java.util.Date date = Calendar.getInstance().getTime();
        String reportDate = df.format(date);
        return reportDate;
    }
    public static String getDateSimple(String firstDate){
        String date = firstDate;
        String hour = date.substring(11,13);
        String minute = date.substring(13,16);
        int correctedHour = Integer.parseInt(hour);
        String correctedDate = correctedHour + minute;
        return correctedDate;

    }
    public static String getJustDate(String firstDate){
        String date = firstDate;
        String month = date.substring(0,2);
        String day = date.substring(3,5);
        int correctedMonth = Integer.parseInt(month);
        String correctedDate1 = correctedMonth + "_" + day;
        String hour = date.substring(11,13);
        String minute = date.substring(14,16);
        String second = date.substring(17,19);
        int correctedHour = Integer.parseInt(hour);
        String correctedDate2 = correctedHour + "_"+ minute+"_"+second;
        return correctedDate1+"_"+correctedDate2;

    }
}
