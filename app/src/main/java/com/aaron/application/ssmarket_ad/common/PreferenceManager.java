package com.aaron.application.ssmarket_ad.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PreferenceManager {
    private static PreferenceManager manager;
    private SharedPreferences sp;

    private static final String APP_PREFERENCE = "APP_PREFERENCE";
    public static final String PREFERENCE_TOKEN = "PREFERENCE_TOKEN";
    public static final String PREFERENCE_CALENDAR = "PREFERENCE_CALENDAR";

    public static final String PREFERENCE_AD_T = "PREFERENCE_AD_T";
    public static final String PREFERENCE_AD_A = "PREFERENCE_AD_A";
    public static final String PREFERENCE_AD_B = "PREFERENCE_AD_B";
    public static final String PREFERENCE_AD_C = "PREFERENCE_AD_C";

    private PreferenceManager(Context context) {
        sp = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context context) {
        if(manager == null) {
            manager = new PreferenceManager(context);
        }
        return manager;
    }

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).apply();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void put(String key, int value) {
        if (value == 0) {
            sp.edit().remove(key).apply();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void put(String key, long value) {
        if (value == 0) {
            sp.edit().remove(key).apply();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void put(String key, ArrayList<String> value) {
        StringBuilder str = new StringBuilder();
        for (int i=0 ; i<value.size() ; i++) {
            str.append(value.get(i));
            if(i != value.size() - 1) str.append(",");
        }
        sp.edit().putString(key, str.toString()).apply();
    }

    public ArrayList<String> getStringArray(String key, ArrayList<String> defValue) {
        String savedString = sp.getString(key, "");
        if(TextUtils.isEmpty(savedString)) {
            return defValue;
        }

        StringTokenizer st = new StringTokenizer(savedString, ",");
        ArrayList<String> savedList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            savedList.add(st.nextToken());
        }
        return savedList;
    }
}
