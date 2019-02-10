package com.rhahn.myworldtrip.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class for some useful functions needed in different classes
 *
 * @author Robin Hahn
 */
public class Util {

    /**
     * Compares to dates
     *
     * @param dateFrom first date
     * @param dateTo   second date
     * @return true if dateFrom is earlier then dateTO
     */
    public static boolean compareDates(Date dateFrom, Date dateTo) {
        return dateFrom.getTime() < dateTo.getTime();
    }

    /**
     * Returns true if dates are the same
     *
     * @param date1 first date
     * @param date2 second date
     * @return true if dates are the same
     */
    public static boolean isSameDate(Date date1, Date date2) {
        return date1.getTime() == date2.getTime();
    }

    /**
     * Converts a {@link String} to a {@link Date}
     *
     * @param date string in format dd.MM.yyyy
     * @return given date
     */
    public static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns true if a string is uppercase
     *
     * @param text string to check
     * @return true if a string is uppercase
     */
    static boolean isUpperCase(String text) {
        text.replace(" ", "");
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            if (!Character.isUpperCase(aChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns index of country in {@link MyWorldtripData}
     *
     * @param worldtripData data to find country in
     * @param countryData   country to find
     * @return index of country, if it doesn't exists it returns -1
     */
    public static int getCountryIndex(MyWorldtripData worldtripData, CountryData countryData) {
        for (int i = 0; i < worldtripData.getCountries().size(); i++) {
            if (worldtripData.getCountries().get(i).getId() == countryData.getId())
                return i;
        }
        return -1;
    }

    /**
     * Returns if current device has a bigger screen then 7"
     *
     * @param context current context
     * @return true if screen is bigger then 7"
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

    /**
     * Returns resource value from the name of the string resource
     *
     * @param nameValue resource name
     * @param context   current context
     * @return sting of resource
     */
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
