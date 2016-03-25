package com.madsquare.android.madutils.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Data convert utility class
 * Created by Ted
 */
public class ConvertUtils {

    private static final String TAG = ConvertUtils.class.getSimpleName();

    /**
     * 픽셀을 dp로 바꿔주는 메소드
     */
    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return (int) dp;
    }

    /**
     * dp -> px로 변경하는 메소드
     */
    public static int convertDpToPixels(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    /**
     * dp -> px로 변경하는 메소드
     */
    public static int convertLimitDpToPixels(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density > 3) {
            density = 3;
        }
        return (int) (dp * density);
    }

    /**
     * 들어오는 시간을 Millisecond 로 변경하는 메소드
     */
    public static long convertTimeToMillis(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        long uTime = 0;
        try {
            Date mDate = format.parse(time);
            uTime = mDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uTime;
    }

    /**
     * seconds를 HH:MM:SS로 변경을 해주는 메소드
     */
    public static String convertSecondsToString(int seconds) {
        String duration = "00:00";

        if (seconds > 0) {
            try {
                long s = seconds % 60;
                long m = (seconds / 60) % 60;
                long h = (seconds / (60 * 60)) % 24;
                if (h > 0) {
                    duration = String.format("%d:%02d:%02d", h, m, s);
                } else {
                    duration = String.format("%02d:%02d", m, s);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return duration;
            }
        }
        // MadLog.d(TAG, "convertSecondsToString : " + duration);
        return duration;
    }

    /**
     * seconds를 HH:MM로 변경을 해주는 메소드
     */
    public static String convertSecondsToMin(int seconds) {
        try {
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            if (h > 0) {
                return String.format("%d:%02d", h, m);
            } else {
                return String.format("%02d", m);
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String convertMillisToSeconds(long millis) {
        long s = (millis / 1000);
        return String.valueOf(s);
    }

    /**
     * double 형 데이터를 천 단위로 나눈 숫자 형식으로 변환후 string type으로 반환
     */
    public static String convertObjectToString(Object data) {
        if (data == null) {
            data = 0;
        }

        double orgData = (double) data;
        Double doubleData = new Double(orgData);
        int castData = doubleData.intValue();

        return NumberFormat.getIntegerInstance().format(castData);
    }


}
