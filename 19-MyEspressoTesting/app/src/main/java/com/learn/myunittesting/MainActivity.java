package com.learn.myunittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learn.myunittesting.model.CuboidModel;
import com.learn.myunittesting.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainViewModel mainViewModel;

    private EditText edtWidth,
            edtHeight,
            edtLength;
    private TextView tvResult;
    private Button btnCalculateVolume,
            btnCalculateSurfaceArea,
            btnCalculateCircumference,
            btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel(new CuboidModel());

        edtWidth = findViewById(R.id.edt_width);
        edtHeight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        tvResult = findViewById(R.id.tv_result);
        btnCalculateVolume = findViewById(R.id.btn_calculate_volume);
        btnCalculateSurfaceArea = findViewById(R.id.btn_calculate_surface_area);
        btnCalculateCircumference = findViewById(R.id.btn_calculate_circumference);
        btnSave = findViewById(R.id.btn_save);

        btnCalculateVolume.setOnClickListener(this);
        btnCalculateSurfaceArea.setOnClickListener(this);
        btnCalculateCircumference.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String length = edtLength.getText().toString().trim();
        String width = edtWidth.getText().toString().trim();
        String height = edtHeight.getText().toString().trim();

        if (TextUtils.isEmpty(length)) {
            edtLength.setError(getString(R.string.empty_field));
        } else if (TextUtils.isEmpty(width)) {
            edtWidth.setError(getString(R.string.empty_field));
        } else if (TextUtils.isEmpty(height)) {
            edtHeight.setError(getString(R.string.empty_field));
        } else {
            double l = Double.parseDouble(length);
            double w = Double.parseDouble(width);
            double h = Double.parseDouble(height);

            if (v.getId() == R.id.btn_save) {
                mainViewModel.save(l, w, h);
                visible();
            } else if (v.getId() == R.id.btn_calculate_circumference) {
                tvResult.setText(String.valueOf(mainViewModel.getCircumference()));
                gone();
            } else if (v.getId() == R.id.btn_calculate_surface_area) {
                tvResult.setText(String.valueOf(mainViewModel.getSurfaceArea()));
                gone();
            } else if (v.getId() == R.id.btn_calculate_volume) {
                tvResult.setText(String.valueOf(mainViewModel.getVolume()));
                gone();
            }
        }
    }

    private void visible() {
        btnCalculateVolume.setVisibility(View.VISIBLE);
        btnCalculateCircumference.setVisibility(View.VISIBLE);
        btnCalculateSurfaceArea.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
    }

    private void gone() {
        btnCalculateVolume.setVisibility(View.GONE);
        btnCalculateCircumference.setVisibility(View.GONE);
        btnCalculateSurfaceArea.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
    }
}