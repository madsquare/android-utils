package com.madsquare.android.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madsquare.android.madutils.BackPressedHandler;
import com.madsquare.android.madutils.MadLog;
import com.madsquare.android.madutils.utils.DateUtils;
import com.madsquare.android.madutils.utils.SystemUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MadLog.isDebug = true;
        MadLog.d("App Version : " + SystemUtils.getAppVersion(this));
        MadLog.d("Device country code : " + SystemUtils.getDeviceCountryCode(this));
        MadLog.d("Today : " + DateUtils.getTodayWithFormat("yyyy-MM-dd"));
    }

    @Override
    public void onBackPressed(){
        BackPressedHandler.onBackPressed(this);
    }
}
