package com.learn.mybackgroundthread;

public interface MyAsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}
