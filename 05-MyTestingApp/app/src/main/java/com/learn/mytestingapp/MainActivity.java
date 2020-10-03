package com.learn.mytestingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSetValue;
    private TextView tvText;
    private ArrayList<String> names;

    private ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.tv_text);
        btnSetValue = findViewById(R.id.btn_set_value);
        imgPreview = findViewById(R.id.img_preview);

        btnSetValue.setOnClickListener(this);
//        imgPreview.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fronalpstock_big));
        Glide.with(this)
                .load(R.drawable.fronalpstock_big)
                .into(imgPreview);

        names = new ArrayList<>();
        names.add("Narenda Wicaksono");
        names.add("Kevin");
        names.add("Yoza");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_value) {
            Log.d("MainActivity", names.toString());
            StringBuilder name = new StringBuilder();
            for (int i = 0; i <= 2; i++) {
                name.append(names.get(i)).append("\n");
            }
            tvText.setText(name.toString());
        }
    }
}