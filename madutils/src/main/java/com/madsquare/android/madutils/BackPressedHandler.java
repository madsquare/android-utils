package com.madsquare.android.madutils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.madsquare.android.madutils.utils.SystemUtils;

/**
 * 뒤로가기(Back 버튼) 두번 눌러 앱 종료하기
 * Created by Ted
 */
public class BackPressedHandler {

    private static long backKeyPressedTime = 0;
    private static Toast toast;

    public static void onBackPressed(Context context) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(context, context.getString(R.string.toast_back_press_app), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            SystemUtils.onDestroyApp();
        }
    }

    public static void onBackPressedKillProcess(Activity activity) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(activity, activity.getString(R.string.toast_back_press_app), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            SystemUtils.killProcess(activity);
        }
    }
}
