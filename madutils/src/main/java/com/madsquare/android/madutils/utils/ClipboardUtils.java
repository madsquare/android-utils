package com.madsquare.android.madutils.utils;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.madsquare.android.madutils.MadLog;

/**
 * Created by Ted
 */
public class ClipboardUtils {

    private static final String TAG = ClipboardUtils.class.getSimpleName();

    private ClipboardManager clipBoard;

    /**
     * Constructor
     */
    public ClipboardUtils(Context context) {
        clipBoard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        registerClipboardListener();
    }

    /**
     * Register Clipboard
     */
    public void registerClipboardListener() {
        if (clipBoard != null)
            clipBoard.addPrimaryClipChangedListener(mPrimaryClipChangedListener);
    }

    /**
     * Unregister Clipboard
     */
    private void unregisterClipboardListener() {
        if (clipBoard != null) {
            clipBoard.removePrimaryClipChangedListener(mPrimaryClipChangedListener);
            clipBoard = null;
        }
    }

    /**
     * Clipboard Manager Listener
     */
    ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            ClipData clipData = clipBoard.getPrimaryClip();
            MadLog.d(TAG, "clipData.getItemAt(0) : " + clipData.getItemAt(0).getText());
        }
    };

}
