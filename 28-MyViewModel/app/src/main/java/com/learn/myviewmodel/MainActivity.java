package com.learn.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.learn.myviewmodel.adapter.WeatherAdapter;
import com.learn.myviewmodel.model.WeatherItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter adapter;
    private EditText edtCity;
    private ProgressBar progressBar;
    private Button btnCity;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.edt_city);
        progressBar = findViewById(R.id.progress_bar);
        btnCity = findViewById(R.id.btn_city);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MainViewModel.class);

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtCity.getText().toString();

                if (TextUtils.isEmpty(city))
                    return;

                showLoading(true);
                viewModel.setWeather(city);
//                setWeather(city);
            }
        });

        viewModel.getWeathers().observe(this, new Observer<ArrayList<WeatherItems>>() {
            @Override
            public void onChanged(ArrayList<WeatherItems> weatherItems) {
                if (weatherItems != null) {
                    adapter.setData(weatherItems);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /*
    private void setWeather(final String city) {
        final ArrayList<WeatherItems> listItems = new ArrayList<>();

        String apikey = "bf9e5cb06e318d521c58c1af3f5935a3";
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + city + "&units=metric&appid=" + apikey;

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

                    // set data ke adapter
                    adapter.setData(listItems);
                    showLoading(false);
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
    */
}