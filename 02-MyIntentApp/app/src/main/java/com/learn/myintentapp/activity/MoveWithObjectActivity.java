package com.learn.myintentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.learn.myintentapp.R;
import com.learn.myintentapp.model.Person;

public class MoveWithObjectActivity extends AppCompatActivity {

    private TextView tvObject;
    private Person person;

    public static final String EXTRA_PERSON = "extra_person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_with_object);

        initViews();
        getData();
    }

    private void initViews() {
        tvObject = findViewById(R.id.tv_object_received);
    }

    private void getData() {
        person = getIntent().getParcelableExtra(EXTRA_PERSON);
        if (person != null) {
            String text = "Name : " + person.getName()
                    + ",\nEmail : " + person.getEmail()
                    + ",\nAge : " + person.getAge()
                    + ",\nLocation : " + person.getCity();
            tvObject.setText(text);
        }
    }
}