package com.madsquare.android.madutils.utils;

import com.madsquare.android.madutils.MadLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Date related utility class
 * Created by Ted
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    /**
     * 현재 날짜를 기준으로 지난 일 주일간의 날짜 반환
     */
    public static List<String> getLastSevenDays() {
        String format = "M/d";

        List<String> listDates = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        for (int i = 6; i > -1; i--) {
            long lastDay = i * 24 * 60 * 60 * 1000;

            Date newDate = new Date(System.currentTimeMillis() - lastDay);
            String result = dateFormat.format(newDate);

            listDates.add(result);
            MadLog.d(TAG, "getLastWeekDates : result = " + result);
        }
        return listDates;
    }


    /**
     * 데이터 포맷으로 날짜 반환
     */
    public static String getDateWithFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date dateResult = null;
        try {
            dateResult = format.parse(date);
            MadLog.d(TAG, "- dateResult = " + dateResult);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if (dateResult != null) {
            long dataMillis = dateResult.getTime();

            String resultFormat = "yyyyMMdd";

            SimpleDateFormat dateFormat = new SimpleDateFormat(resultFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date newDate = new Date(dataMillis);

            return dateFormat.format(newDate);
        }
        return null;
    }

    public static String getSocialTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date dateResult = format.parse(date);
            return format.format(dateResult);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLast7daysOr7weeksDate(boolean isLast7days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date today = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        if (isLast7days) {
            calendar.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, -6 * 7);
        }

        Date newDate = calendar.getTime();

        return format.format(newDate);
    }

    /**
     * 원하는 포맷 형태로 오늘 날짜 반환
     */
    public static String getTodayWithFormat(String resultFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(resultFormat);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String get7WeeksAgoFirstDate() {
        //특정 날짜가 속한 주의 시작 날짜 반환 : 월요일
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7 * 6);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date mondayDate = calendar.getTime();
        return format.format(mondayDate);
    }

    public static String getCurrentWeekLastDate() {
        //이번주의 마지막 날짜 반환 : 일요일
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 7); //일주일의 시작이 일요일이므로 다음주 일요일 날짜로 셋팅
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Date mondayDate = calendar.getTime();
        return format.format(mondayDate);
    }

    /**
     * 현 지역 Time Zone Offset을 가져오는 메소드
     */
    public static String getCurrentTimezoneOffset() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = -mTimeZone.getRawOffset();

        return String.valueOf(TimeUnit.MINUTES.convert(mGMTOffset, TimeUnit.MILLISECONDS));
    }

}
