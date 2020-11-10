package com.learn.mysharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.learn.mysharedpreferences.model.UserModel;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class UserPreference {

    private static final String PREFS_NAME = "user_pref";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String PHONE_NUMBER = "phone";
    private static final String LOVE_MU = "islove";

    private final SharedPreferences preferences;

    public UserPreference(Context mContext) {
        preferences = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Setter
    public void setUser(UserModel value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME, value.getName());
        editor.putString(EMAIL, value.getEmail());
        editor.putInt(AGE, value.getAge());
        editor.putString(PHONE_NUMBER, value.getPhoneNumber());
        editor.putBoolean(LOVE_MU, value.isLove());
        editor.apply();
    }

    // Getter
    UserModel getUser() {
        UserModel model = new UserModel();
        model.setName(preferences.getString(NAME, ""));
        model.setEmail(preferences.getString(EMAIL, ""));
        model.setAge(preferences.getInt(AGE, 0));
        model.setPhoneNumber(preferences.getString(PHONE_NUMBER, ""));
        model.setLove(preferences.getBoolean(LOVE_MU, false));

        return model;
    }
}
