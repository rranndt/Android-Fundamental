package com.learn.lastsubmission.Ui.Activity.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Preferences;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Receiver.NotificationReceiver;
import com.learn.lastsubmission.Ui.Activity.Home.MainActivity;

import java.util.Calendar;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout clLanguage;
    private ConstraintLayout clEnglish, clIndonesia;
    private ImageView ivCheckEnglish, ivCheckIndonesia;
    private SwitchCompat switchCompat;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Preferences pref;

    private String langPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViews();


        loadLocale();
        setSwitchCompat();

        preferences = getSharedPreferences(Preferences.PREFS_NAME, MODE_PRIVATE);
        int notif = preferences.getInt(Preferences.PREFS_NAME, 0);
        switchCompat.setChecked(notif == 1);
    }

    private void initViews() {
        clEnglish = findViewById(R.id.clEnglish);
        clIndonesia = findViewById(R.id.clIndonesia);
        ivCheckEnglish = findViewById(R.id.ivCheckEnglish);
        ivCheckIndonesia = findViewById(R.id.ivCheckIndonesia);
        switchCompat = findViewById(R.id.switchReminder);

        clEnglish.setOnClickListener(this);
        clIndonesia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clIndonesia) {
            changeLanguage(Const.INDONESIA);
        } else if (view.getId() == R.id.clEnglish) {
            changeLanguage(Const.ENGLISH);
        }
    }

    private void loadLocale() {
        preferences = getSharedPreferences(Preferences.PREFS_NAME, Activity.MODE_PRIVATE);
        langPrefs = preferences.getString(Preferences.LANGUAGE, Const.ENGLISH);
        if (langPrefs.equals(Const.INDONESIA)) {
            ivCheckIndonesia.setVisibility(View.VISIBLE);
        } else if (langPrefs.equals(Const.ENGLISH)) {
            ivCheckEnglish.setVisibility(View.VISIBLE);
        }
    }

    public void changeLanguage(String language) {
        if (language.equals(Const.INDONESIA)) {
            ivCheckIndonesia.setVisibility(View.VISIBLE);
            ivCheckEnglish.setVisibility(View.INVISIBLE);
        } else if (language.equals(Const.ENGLISH)) {
            ivCheckEnglish.setVisibility(View.VISIBLE);
            ivCheckIndonesia.setVisibility(View.INVISIBLE);
        }

        setLocale(language, this);
        Intent intent = new Intent(this, MainActivity.class);
        finishAffinity();
        startActivity(intent);
    }

    public void setLocale(String lang, Activity activity) {
        pref = new Preferences(this);

        Locale locale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        pref.setLocale(lang);
    }

    private void setSwitchCompat() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setReminder(getApplicationContext());
                    editor = preferences.edit();
                    editor.putInt(Preferences.PREFS_NAME, 1);
                    editor.apply();
                    Toast.makeText(SettingActivity.this, getString(R.string.notification_on), Toast.LENGTH_SHORT).show();
                } else {
                    cancelReminder(getApplicationContext());
                    editor = preferences.edit();
                    editor.putInt(Preferences.PREFS_NAME, 0);
                    editor.apply();
                    Toast.makeText(SettingActivity.this, getString(R.string.notification_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setReminder(Context applicationContext) {
        Intent intent = new Intent(applicationContext, NotificationReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void cancelReminder(Context applicationContext) {
        Intent intent = new Intent(SettingActivity.this, NotificationReceiver.class);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}