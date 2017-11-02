package com.aaron.application.ssmarket_ad.common;

/**
 * Created by AaronMac on 2017. 7. 24..
 */

public enum AdType {
    T("(T)", PreferenceManager.PREFERENCE_AD_T, PreferenceManager.PREFERENCE_CALENDAR_T, 1),
    A("(a)", PreferenceManager.PREFERENCE_AD_A, PreferenceManager.PREFERENCE_CALENDAR_A, 2),
    B("(b)", PreferenceManager.PREFERENCE_AD_B, PreferenceManager.PREFERENCE_CALENDAR_B, 3),
    C("(c)", PreferenceManager.PREFERENCE_AD_C, PreferenceManager.PREFERENCE_CALENDAR_C, 4);

    private String typeName;
    private String prefKey;
    private String calendarKey;
    private int requestCode;

    AdType(String typeName, String prefKey, String calendarKey, int requestCode) {
        this.typeName = typeName;
        this.prefKey = prefKey;
        this.calendarKey = calendarKey;
        this.requestCode = requestCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getPrefKey() {
        return prefKey;
    }

    public String getCalendarKey() {
        return calendarKey;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public static AdType getType(String name) {
        for(AdType type : AdType.values()) {
            if(name.contains(type.getTypeName())) {
                return type;
            }
        }

        return T;
    }
}
