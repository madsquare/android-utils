package com.madsquare.android.madutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.madsquare.android.madutils.MadLog;

/**
 * Android system related utility class
 * Created by Ted
 */
public class SystemUtils {

    private static final String TAG = SystemUtils.class.getSimpleName();

    /**
     * 현재 스크린의 방향을 구하는 메소드
     * 가로 : 홀수, 세로 : 짝수
     */
    public static int getCurrentOrientation(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getRotation();
    }

    /**
     * 상태바를 숨기는 메소드
     */
    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);

        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 상태바를 보여주는 메소드
     */
    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);

        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * status bar height 반환
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 해상도의 가로 길이를 픽셀로 구하는 메소드
     */
    private static int getScreenWidthPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 해상도의 세로 길이를 픽셀로 구하는 메소드
     */
    private static int getScreenHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 핸드폰 화면의 가로 길이를 구하는 메소드
     */
    public static int getScreenWidth(Context context) {
        int width = getScreenWidthPx(context);
        int height = getScreenHeightPx(context);
        if (width > height) {
            return height;
        } else {
            return width;
        }
    }

    /**
     * 핸드폰 화면의 세로 길이를 구하는 메소드
     */
    public static int getScreenHeight(Context context) {
        int width = getScreenWidthPx(context);
        int height = getScreenHeightPx(context);
        if (width > height) {
            return width;
        } else {
            return height;
        }
    }

    /**
     * 화면 비울을 구하는 메소드
     */
    public static float getScreenRotate(Context context) {
        return (float) getScreenHeight(context) / (float) getScreenWidth(context);
    }

    /**
     * 해상도의 가로 길이를 dp로 구하는 메소드
     */
    public static int getScreenWidthDp(Context context) {
        return ConvertUtils.convertPixelsToDp(context.getResources().getDisplayMetrics().widthPixels, context);
    }

    /**
     * 해상도의 세로 길이를 dp로 구하는 메소드
     */
    public static int getScreenHeightDp(Context context) {
        return ConvertUtils.convertPixelsToDp(context.getResources().getDisplayMetrics().heightPixels, context);
    }

    /**
     * 단말기 위치에 따른 국가명 반환
     */
    public static String getDeviceCountryCode(Context context) {
        return String.valueOf(context.getResources().getConfiguration().locale.getCountry());
    }

    /**
     * 현재 디바이스의 언어를 가져오는 메소드
     */
    public static String getDeviceLanguage(Context context) {
        return String.valueOf(context.getResources().getConfiguration().locale.toString());
    }

    /**
     * 특정 에디트뷰가 포커스 얻었을 때 소프트웨어 키보드를 보여주는 메소드
     */
    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * Network 연결 상태 확인
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * 와이파이 상태 확인하는 메소드
     */
    public static boolean isWifiState(final Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 3G/4G 상태 확인하는 메소드
     */
    public static boolean isDataNetworkState(final Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 소프트웨어 키보드를 내리는 메소드
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 단말기의 소프트웨어 버전 정보 반환
     */
    public static int getSoftWareVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 사용자 설정 중 자동회전 여부 반환.
     */
    public static boolean getAutoScreenRotateState(Context context) {
        boolean isAutoRotate;
        int rotateState = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        isAutoRotate = rotateState != 0;
        MadLog.d(TAG, "getAutoScreenRotateState : isAutoRotate = " + isAutoRotate);
        return isAutoRotate;
    }

    /**
     * 단말기에 설치된 어플리케이션의 아이콘 반환
     */
    public static Drawable getApplicationIcon(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return context.getPackageManager().getApplicationIcon(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 현재 Version Name 반환
     */
    public static String getAppVersion(Context context) {
        String version = null;

        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 현재 Version Code 반환
     */
    public static int getVersionCode(Context context, String packageName) {
        int v = 0;
        try {
            v = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * 현재 테블릿인지 확인하는 메소드
     *
     * @return true : 테블릿, false : 모바일
     */
    public static boolean isTablet(Context context) {
        return (isTabletPhoneType(context) || isTabletDevice(context));
    }

    /**
     * 테블릿인지 phone Type 확인하는 메소드
     *
     * @param context context
     * @return true : 테블릿, false : 모바일
     */
    private static boolean isTabletPhoneType(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MadLog.d(TAG, "isTablet : " + (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE));
        return (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE);
    }

    /**
     * 테블릿 디바이스인지 확인하는 메소드
     *
     * @param context context
     * @return true : 테블릿, false : 모바일
     */
    private static boolean isTabletDevice(Context context) {
        // Verifies if the Generalized Size of the device is XLARGE to be considered a Tablet
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                MadLog.d(TAG, "isTabletDevice : true");
                return true;
            }
        }
        MadLog.d(TAG, "isTabletDevice : false");
        return false;
    }

    /**
     * 화면 FullScreen으로 변경 해주는 메소드
     */
    public static void setUIFullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    /**
     * 앱을 강제종료하는 메소드
     */
    public static void onDestroyApp() {
        System.exit(0);
    }

    /**
     * 앱 Process 까지 종료하는 메소드
     */
    public static void killProcess(Activity activity) {
        activity.moveTaskToBack(true);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
