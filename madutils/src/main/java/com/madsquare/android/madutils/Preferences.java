package com.madsquare.android.madutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Android Shared Preferences
 */
public class Preferences {

    private static final String NAME = "Madsquare";

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, Long.toString(value));
        edit.apply();
    }

    public static int loadInt(Context context, String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    public static String loadString(Context context, String key, String defaultValue) {
        if (context == null) {
            return null;
        }

        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public static boolean loadBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public static long loadLong(Context context, String key, long defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String value = pref.getString(key, Long.toString(defaultValue));
        return Long.parseLong(value);
    }

}
