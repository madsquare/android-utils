package com.madsquare.android.madutils.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.madsquare.android.madutils.MadLog;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

/**
 * String related utility class
 * Created by Ted
 */
public class StringUtils {

    private static final String TAG = StringUtils.class.getSimpleName();

    /**
     * 랜덤 스트링 값 반환
     */
    public static String getRandomString(int length) {
        Random random = new Random((new Date()).getTime());

        char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                '4', '5', '6', '7', '8', '9'};

        String result = "";

        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(values.length);
            result += values[idx];
        }
        MadLog.d(TAG, "getRandomString : " + result);
        return result;
    }

    public static String getRandomCode() {
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 20; i++) {
            if (rnd.nextBoolean()) {
                buf.append((char) (rnd.nextInt(26) + 97));
            } else {
                buf.append((rnd.nextInt(10)));
            }
        }

        return String.valueOf(buf);
    }

    /**
     * URI 로 PATH 를 반환하는 메소드
     */
    public static String getPathFromURI(Context context, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            MadLog.d(TAG, "- PATH = " + filePath);

            return filePath;
        } else {
            MadLog.d(TAG, "- PATH = " + uri.getPath());
            return uri.getPath();               // FOR OI/ASTRO/Dropbox etc
        }
    }

    /**
     * 천단위에서 콤마로 표시되는 문자열 반환
     */
    public static String convertNumberDefaultFormat(int number) {
        return NumberFormat.getIntegerInstance().format(number);
    }

    /**
     * 문자열 자르기
     *
     * @param phrase 원본 문자열
     * @param start  start 부터 끝까지
     * @return 잘린 문자열
     */
    public static String cutPhrase(String phrase, int start) {
        return phrase.substring(start);
    }

    /**
     * 문자열 자르기
     *
     * @param phrase 원본 문자열
     * @param start  start 부터
     * @param end    end 까지
     * @return 잘린 문자열
     */
    public static String cutPhrase(String phrase, int start, int end) {
        return phrase.substring(start, end);
    }

    /**
     * 문자열 나누기
     *
     * @param phrase  원분 문자열
     * @param divider 문자열을 나눌 분리자
     * @return 나눈 문자열 array
     */
    public static String[] splitParse(String phrase, String divider) {
        return phrase.split(divider);
    }

    /**
     * 문자열 좌우 공백 없애기
     *
     * @param phrase 원본 문자열
     * @return 공백을 없앤 문자열
     */
    public static String removeGapParse(String phrase) {
        return phrase.trim();
    }
}
