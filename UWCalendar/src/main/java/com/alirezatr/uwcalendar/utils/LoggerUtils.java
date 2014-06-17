package com.alirezatr.uwcalendar.utils;

import android.util.Log;

public class LoggerUtils {

    public static void LogError(String tag, String message, Class<?> parentClass) {
        Log.e("UWCalendar, " + tag + ": " + parentClass, message);
    }
}
