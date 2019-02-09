package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.rhahn.myworldtrip.Activities.TimelineActivity;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static boolean compareDates(Date dateFrom, Date dateTo) {
        if (dateFrom.getTime() < dateTo.getTime())
            return true;
        return false;
    }

    public static boolean isSameDate(Date date1, Date date2) {
        if (date1.getTime() == date2.getTime())
            return true;
        return false;
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date1 = sdf.parse(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUpperCase(String text) {
        text.replace(" ", "");
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isUpperCase(chars[i])) {
                return false;
            }
        }
        return true;
    }

    public static int getCountryIndex(MyWorldtripData worldtripData, CountryData countryData) {
        for (int i = 0; i < worldtripData.getCountries().size(); i++) {
            if (worldtripData.getCountries().get(i).getId() == countryData.getId())
                return i;
        }
        return -1;
    }

    public static boolean isTablet(Context context)
    {
        Display display = ((TimelineActivity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        double wInches =   displayMetrics.widthPixels / (double)displayMetrics.densityDpi;
        double hInches = displayMetrics.heightPixels / (double)displayMetrics.densityDpi;

        double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));
        return (screenDiagonal >= 7.0);
    }

}
