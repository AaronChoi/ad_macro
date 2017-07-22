package com.aaron.application.ssmarket_ad;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.aaron.application.ssmarket_ad.common.PreferenceManager;
import com.facebook.stetho.Stetho;


public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    public static String TOKEN;

    @Override
    public void onCreate() {
        super.onCreate();

        // for stetho
        // trust all certificates for debug application
        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            Log.d(TAG, "trust all certificates and initialize stetho library for debugging mode");
            Stetho.initializeWithDefaults(this);
        }

        TOKEN = PreferenceManager.getInstance(this).getString(PreferenceManager.PREFERENCE_TOKEN, null);
    }
}
