package com.madsquare.android.madutils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.madsquare.android.madutils.MadLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View related utility class
 * Created by Ted
 */
public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    /**
     * 비디오의 16:9 높이를 구하는 메소드
     */
    public static int getVideoHeight(int width) {
        return width * 9 / 16;
    }

    /**
     * 비디오의 16:9 가로 길이를 구하는 메소드
     */
    public static int getVideoWidth(int height) {
        return height * 16 / 9;
    }

    /**
     * 비율을 구하는 메소드
     */
    public static int getScreenRate(int width, int height, int xRate, int yRate) {
        if (width == 0) {
            return height * xRate / yRate;
        } else {
            return width * yRate / xRate;
        }
    }

    /**
     * 에디트 텍스트 뷰 외 뷰를 터치하였을 경우 소프트웨어 키보드는 내리는 메소드
     */
    public static void hideKeyboardTouchOutside(final Activity activity, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    SystemUtils.hideSoftKeyboard(activity);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardTouchOutside(activity, innerView);
            }
        }
    }

    /**
     * TextView에서 일정 영역 Bold 처리하는 메소드
     */
    public static void setTextViewBoldPartial(final TextView view, final String fulltext, final String subtext) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i > 0) {
            str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), i, i + subtext.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }

    /**
     * TextView에서 일정 영역 Color 변경하는 메소드
     */
    public static void setTextViewColorPartial(final TextView view, final String fulltext, final String subtext, final int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i > 0) {
            str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 라운드 배경을 가진 커스텀 토스트
     */
    public static Toast toast;

    public static void showToast(Context context, String text) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View view = inflater.inflate(R.layout.layout_toast, null);
//
//		TextView textView = (TextView) view.findViewById(R.id.text);
//		textView.setText(text);
//		toast.setView(view);

        toast.show();
    }

    /**
     * 비트맵을 파일형태로 변환하는 메소드
     */
    public static File getFileFromBitmap(Bitmap bitmap, String path) {
        MadLog.d(TAG, "getFileFromBitmap : path = " + path);
        File fileCacheItem = new File(path);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            return fileCacheItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * URL 에서 비트맵 반환
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 해당 포지션으로 애니메이션을 주며 이동하는 메소드
     * (단, 이 메소드 사용 후 ScrollListener를 다시 등록해줘야함)
     *
     * @param view     AbsListView
     * @param position move position
     */
    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
        View child;
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            child = view.getChildAt(index);
        } else {
            child = null;
        }

        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.setSelection(position);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) {
            }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }

    /**
     * 문자열에 link 추가
     */
    public static void addLinkToTextview(final TextView view, String pattern, final String link) {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return link;
            }
        };
        Linkify.addLinks(view, Pattern.compile(pattern), null, null, filter);
    }
}
