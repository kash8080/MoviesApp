package com.kashyapmedia.moviesdemo.util;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferenceUtils {


    public static void setLastLanguage(Context context, String val){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("LastLanguage",val).apply();
    }
    public static String getLastLanguage(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("LastLanguage", "");
    }

}
