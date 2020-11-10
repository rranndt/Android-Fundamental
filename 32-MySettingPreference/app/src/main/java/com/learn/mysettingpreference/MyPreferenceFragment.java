package com.learn.mysettingpreference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String DEFAULT_VALUE = "Tidak Ada";
    private String NAME;
    private String EMAIL;
    private String AGE;
    private String PHONE;
    private String LOVE;

    private EditTextPreference namePreference;
    private EditTextPreference emailPreference;
    private EditTextPreference agePreference;
    private EditTextPreference phonePreference;
    private CheckBoxPreference isLoveMuPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummary();
    }

    private void init() {
        NAME = getResources().getString(R.string.key_name);
        EMAIL = getResources().getString(R.string.key_email);
        AGE = getResources().getString(R.string.key_age);
        PHONE = getResources().getString(R.string.key_phone);
        LOVE = getResources().getString(R.string.key_love);

        namePreference = (EditTextPreference) findPreference(NAME);
        emailPreference = (EditTextPreference) findPreference(EMAIL);
        agePreference = (EditTextPreference) findPreference(AGE);
        phonePreference = (EditTextPreference) findPreference(PHONE);
        isLoveMuPreference = (CheckBoxPreference) findPreference(LOVE);
    }

    private void setSummary() {
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
        namePreference.setSummary(preferences.getString(NAME, DEFAULT_VALUE));
        emailPreference.setSummary(preferences.getString(EMAIL, DEFAULT_VALUE));
        agePreference.setSummary(preferences.getString(AGE, DEFAULT_VALUE));
        phonePreference.setSummary(preferences.getString(PHONE, DEFAULT_VALUE));
        isLoveMuPreference.setChecked(preferences.getBoolean(LOVE, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(NAME)) {
            namePreference.setSummary(sharedPreferences.getString(NAME, DEFAULT_VALUE));
        }

        if (key.equals(EMAIL)) {
            emailPreference.setSummary(sharedPreferences.getString(EMAIL, DEFAULT_VALUE));
        }

        if (key.equals(AGE)) {
            agePreference.setSummary(sharedPreferences.getString(AGE, DEFAULT_VALUE));
        }

        if (key.equals(PHONE)) {
            phonePreference.setSummary(sharedPreferences.getString(PHONE, DEFAULT_VALUE));
        }

        if (key.equals(LOVE)) {
            isLoveMuPreference.setChecked(sharedPreferences.getBoolean(LOVE, false));
        }
    }
}
