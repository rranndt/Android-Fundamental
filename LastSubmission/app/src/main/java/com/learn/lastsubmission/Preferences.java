package com.learn.lastsubmission;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class Preferences {

    public static final String PREFS_NAME = "prefs";
    public static final String LANGUAGE = "lang";

    private final SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context mContext) {
        preferences = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setLocale(String lang) {
        editor = preferences.edit();
        editor.putString(LANGUAGE, lang);
        editor.apply();
    }
}
