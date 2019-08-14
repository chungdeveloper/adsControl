package com.module.ads.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChungLD on 20/09/2016.
 */
@SuppressWarnings("ALL")
public class Utils {
    public static String TIME_FOMART = "hh:mm (a)";
    public static String DATE_FOMART = "yyyy-mm'T'hh:mm:ss";
    public static String DATE_TIME_FOMART = "yyyy-MM-dd HH:mm:ss";
    public static String DAY = "dd";
    public static String DATE_TIME_FOMART1 = "MM/dd/yyyy HH:mm:ss";


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getDate(long time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String date = DateFormat.format(format, cal).toString();
        return date;
    }

    public static Date getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }


    public static int getMinutes(long time) {
        return (int) (time / 60);
    }

    public static String getTimeFromMillis(long time) {
        int h, m, s;
        h = m = s = 0;
        s = (int) (time / 1000);
        if (s >= 60) {
            m = s / 60;
            s = s % 60;
        }

        if (m >= 60) {
            h = m / 60;
            m = m % 60;
        }
        if (h != 0)
            return String.format("%02d:%02d:%02d", h, m, s);

        return String.format("%02d:%02d", m, s);
    }

    public static int randInt(int min, int max) {
        Random r = new Random();
        int randomNum = r.nextInt(max - min) + min;
        return randomNum;
    }

    public static boolean checkConnect(Context context) {
        ConnectivityManager connM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connM.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || connM.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
            return true;
        } else if (connM.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public static int convertGender(String s) {
        int g = 0;
        switch (s) {
            case "Nam":
                g = 1;
                break;
            case "Nữ":
                g = 2;
                break;
        }
        return g;
    }

    public static int convertPixelToDp(int pixel) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, displaymetrics);
    }

    public static int convertDpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static long getTimeStampFromString(String value) {
        long result = 0;
        value = value.trim();
        value = value.replace(",", ".");
        String[] values = value.split("\\:");
        result += convertStringToLong(values[0]) * 60 * 60 * 1000;
        result += convertStringToLong(values[1]) * 60 * 1000;
        result += convertStringToLong(values[2].replace(".", ""));
        return result;
    }

    public static long convertStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
