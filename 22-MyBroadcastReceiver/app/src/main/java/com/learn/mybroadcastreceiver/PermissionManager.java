package com.learn.mybroadcastreceiver;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionManager {

    public static void check(Activity activity, String persmission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, persmission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{persmission}, requestCode);
        }
    }
}
