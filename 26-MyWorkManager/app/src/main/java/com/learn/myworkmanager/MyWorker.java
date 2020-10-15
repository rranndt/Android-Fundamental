package com.learn.myworkmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MyWorker extends Worker {

    private static final String TAG = "MyWorker";
    public static final String APP_ID = "bf9e5cb06e318d521c58c1af3f5935a3";
    public static final String EXTRA_CITY = "city";
    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "channel_01";
    public static final String CHANNEL_NAME = "dicoding_channel";

    private Result resultStatus;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String dataCity = getInputData().getString(EXTRA_CITY);
        Result status = getCurrentWeather(dataCity);
        return status;
    }

    private Result getCurrentWeather(final String city) {
        Log.d(TAG, "getCurrentWeather: Mulai.....");
        SyncHttpClient client = new SyncHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APP_ID;
        Log.d(TAG, "getCurrentWeather: " + url);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);

                    String currentWeather = responseObject.getJSONArray("weather").getJSONObject(0).getString("main");
                    String description = responseObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    double tempInkelvin = responseObject.getJSONObject("main").getDouble("temp");

                    double tempInCelcius = tempInkelvin - 273;
                    String temperature = new DecimalFormat("##.##").format(tempInCelcius);

                    String title = "Current Weather in " + city;
                    String message = currentWeather + ", " + description + " with " + temperature + " celcius";

                    showNotification(title, message);

                    Log.d(TAG, "onSuccess: Selesai.....");
                    resultStatus = Result.success();
                } catch (Exception e) {
                    showNotification("Get Current Weather Not Success", e.getMessage());
                    Log.d(TAG, "onSuccess: Gagal.....");
                    resultStatus = Result.failure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return resultStatus;
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notification.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification.build());
        }
    }
}
