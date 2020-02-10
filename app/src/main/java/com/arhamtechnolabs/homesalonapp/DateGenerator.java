package com.arhamtechnolabs.homesalonapp;

import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateGenerator {

    public static List<DateHelper> generateDate() {
        List<DateHelper> list = new ArrayList<>();
        for (int i=0;i<7;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Date date = calendar.getTime();
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            String day          = (String) DateFormat.format("dd",   date); // 20
            String monthString  = (String) DateFormat.format("MMM",  date); // Jun

            DateHelper dateHelper = new DateHelper(day, dayOfTheWeek, monthString);
            list.add(dateHelper);
        }

        return list;

    }

    public static String getDate(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, i);
        Date date = calendar.getTime();
        String dayOfTheWeek = (String) DateFormat.format("dd-MMM-yy", date);
        return dayOfTheWeek;
    }

    public static Date convertStringToDate(String d) {
        try{
            Date date1=new SimpleDateFormat("dd-MMM-yy").parse(d);
            return date1;

        }catch (Exception e ){

        }

        return null;


    }

}
