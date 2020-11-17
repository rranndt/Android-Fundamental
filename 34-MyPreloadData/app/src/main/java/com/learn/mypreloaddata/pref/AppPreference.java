package com.learn.mypreloaddata.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class AppPreference {

    private static final String PREFS_NAME = "Mahasiswa_pref";
    private static final String APP_FIRST_RUN =  "app_first_run";
    private SharedPreferences preferences;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APP_FIRST_RUN, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return preferences.getBoolean(APP_FIRST_RUN, true);
    }
}
