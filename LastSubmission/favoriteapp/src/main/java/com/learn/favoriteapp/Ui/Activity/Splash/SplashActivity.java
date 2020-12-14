package com.learn.favoriteapp.Ui.Activity.Splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.favoriteapp.R;
import com.learn.favoriteapp.Ui.Activity.Favorite.FavoriteActivity;

public class SplashActivity extends AppCompatActivity {

    private String appVersion;
    private TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvAppVersion = findViewById(R.id.tvAppVersion);
        setSplash();
    }

    @SuppressLint("SetTextI18n")
    private void setSplash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 2000);

        tvAppVersion.setText(getString(R.string.app_version) + " " + appVersion);
    }
}