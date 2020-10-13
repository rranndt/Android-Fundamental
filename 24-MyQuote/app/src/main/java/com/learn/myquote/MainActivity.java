package com.learn.myquote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuote,
            tvAuthor;
    private ProgressBar progressBar;
    private Button btnAllQuote;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuote = findViewById(R.id.tv_quote);
        tvAuthor = findViewById(R.id.tv_author);
        progressBar = findViewById(R.id.progress_bar);
        btnAllQuote = findViewById(R.id.btn_all_quotes);

        btnAllQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListQuotesActivity.class));
            }
        });

        getRandomQuote();
    }

    private void getRandomQuote() {
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://programming-quotes-api.herokuapp.com/quotes/random";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Jika koneksi berhasil
                progressBar.setVisibility(View.GONE);

                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);

                    String quote = responseObject.getString("en");
                    String author = responseObject.getString("author");

                    tvQuote.setText(quote);
                    tvAuthor.setText(author);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Jika koneksi gagal
                progressBar.setVisibility(View.GONE);

                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Not FOund";
                        break;
                    case 404:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}