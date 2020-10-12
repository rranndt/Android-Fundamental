package com.learn.myservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.learn.myservice.DummyAsyncCallback;

import java.lang.ref.WeakReference;

// Langkah 1 : Buat interface
// Langkah 2 : Implement interface dan implement method preAsync dan postAsync
public class MyService extends Service implements DummyAsyncCallback {

    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        // Langkah 4 : Inisialisasi dan jalankan AsyncTask
        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

        return START_STICKY;
    }

    // Langkah 5 : Tambahkan aksi di callback
    @Override
    public void preAsync() {
        Log.d(TAG, "preAsync: Mulai......");
    }

    @Override
    public void postAsync() {
        Log.d(TAG, "postAsync: Selesai......");
        stopSelf();
    }

    // Langkah 3 : Buat AsyncTask dan WeakReference
    private static class DummyAsync extends AsyncTask<Void, Void, Void> {

        private final WeakReference<DummyAsyncCallback> callback;

        public DummyAsync(DummyAsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: ");
            callback.get().postAsync();
        }
    }
}
