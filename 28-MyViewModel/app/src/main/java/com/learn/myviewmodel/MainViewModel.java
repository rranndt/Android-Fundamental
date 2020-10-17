package com.learn.myviewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.myviewmodel.model.WeatherItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {

    // MutableLiveData
    // Bisa kita ubah value-nya
    private MutableLiveData<ArrayList<WeatherItems>> listWeather = new MutableLiveData<>();

    void setWeather(final String cities) {
        // Request API
        final ArrayList<WeatherItems> listItems = new ArrayList<>();

        String apikey = "bf9e5cb06e318d521c58c1af3f5935a3";
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + apikey;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("onSuccess: ", result);
                try {
                    // Parsing JSON
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems();
                        weatherItems.setId(weather.getInt("id"));
                        weatherItems.setName(weather.getString("name"));

                        weatherItems.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItems.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));

                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelcius = tempInKelvin - 273;
                        weatherItems.setTemperature(new DecimalFormat("##.##").format(tempInCelcius));

                        listItems.add(weatherItems);
                    }

                    // Merubah Value di LiveData
                    // SetValue bekenja di main thread
                    // postValue bekerja di background thread
                    listWeather.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    // LiveData
    // Bersifat read-only
    LiveData<ArrayList<WeatherItems>> getWeathers() {
        return listWeather;
    }
}
