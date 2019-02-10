package com.rhahn.myworldtrip.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.rhahn.myworldtrip.Activities.TimelineActivity;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util {

    public static boolean compareDates(Date dateFrom, Date dateTo) {
        return dateFrom.getTime() < dateTo.getTime();
    }

    public static boolean isSameDate(Date date1, Date date2) {
        return date1.getTime() == date2.getTime();
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

    /**
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        Display display;
        display = ((Activity) context).getWindowManager().getDefaultDisplay();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        double wInches = displayMetrics.widthPixels / (double) displayMetrics.densityDpi;
        double hInches = displayMetrics.heightPixels / (double) displayMetrics.densityDpi;

        double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));
        return (screenDiagonal >= 7.0);
    }

    public static String getStringValueFromName(String nameValue, Context context) {
        int resourceId = context.getResources().getIdentifier(nameValue, "string", context.getPackageName());
        return context.getString(resourceId);
    }

    /**
     * Returns the number of days between two dates.
     * The order doesn't matter
     *
     * @param date1 date1
     * @param date2 date2
     * @return days between two dates
     */
    public static int getDaysBetweenTwoDates(Date date1, Date date2) {
        int days;

        //if null return -1
        if (date1 == null || date2 == null)
            return -1;

        //make sure the days are positiv
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        days = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return days;
    }
}
