package com.example.my_news.utils;

import android.content.Context;
import android.content.SharedPreferences;

//SharedPreferencesData: this class stores the data specified by the user with regards
//to their article topics of interest
public class SharedPreferencesData {

    public static final String SEARCH_ARTICLE_NOTIFICATION_VALUES =
            "SEARCH_ARTICLE_NOTIFICATIONS_VALUES";
    public static final String NOTIFICATION_WIDGET = "NOTIFICATION_WIDGET";
    public static final String SHARED_PREFERENCES = "sHARED_PREFERENCES";
    public static final String SHARED_VIEW = "SHARED_VIEW";

    public SharedPreferencesData() {
        //Empty Constructor
    }

    //Saves notification values/topics specified by the user whenever the change
    public void saveToSharedPrefs(Context context, String s) {
        SharedPreferences sp = context.getSharedPreferences(
                SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SEARCH_ARTICLE_NOTIFICATION_VALUES, s);
        editor.apply();
    }

    //Loads those same specifications that were saved ^^
    public String loadSharedPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(SEARCH_ARTICLE_NOTIFICATION_VALUES, null);
    }

    //Returns the notification widget if value is true
    public boolean loadView(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_VIEW,
                Context.MODE_PRIVATE);
        return preferences.getBoolean(NOTIFICATION_WIDGET, true);
    }

    //Saves the notification widget value to SharedPreferences
    public void saveView(Context context, boolean b) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_VIEW,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_WIDGET, b);
        editor.apply();
    }
}
