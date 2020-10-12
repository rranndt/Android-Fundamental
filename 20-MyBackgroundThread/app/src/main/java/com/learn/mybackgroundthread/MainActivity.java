package com.learn.mybackgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    private TextView tvStatus, tvDesc;

    public static final String INPUT_STRING = "Halo ini demo AsyncTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }

    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String text) {
        tvStatus.setText(R.string.status_post);
        if (text != null) {
            tvDesc.setText(text);
        }
    }

    // Berfungsi untuk mengelola data secara asynchronous
    private static class DemoAsync extends AsyncTask<String, Void, String> {
        public static final String LOG_ASYNC = "DemoAsync";
        WeakReference<MyAsyncCallback> myListener;
        DemoAsync(MyAsyncCallback myListener) {
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "status : onPreExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOG_ASYNC, "status : doInBackground");
            String output = null;

            try {
                String input = strings[0];
                output = input + " Selamat Belajar!!";
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d(LOG_ASYNC, Objects.requireNonNull(e.getMessage()));
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(LOG_ASYNC, "status : onPostExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPostExecute(s);
            }
        }
    }
}