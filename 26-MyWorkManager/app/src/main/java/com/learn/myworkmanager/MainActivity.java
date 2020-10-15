package com.learn.myworkmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOneTimeTask,
            btnPeriodicTask,
            btnCancelTask;
    private EditText edtCity;
    private TextView tvStatus;

    private PeriodicWorkRequest periodicWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOneTimeTask = findViewById(R.id.btn_one_time_task);
        edtCity = findViewById(R.id.edt_city);
        tvStatus = findViewById(R.id.tv_status);
        btnPeriodicTask = findViewById(R.id.btn_periodic_task);
        btnCancelTask = findViewById(R.id.btn_cancel_task);

        btnOneTimeTask.setOnClickListener(this);
        btnPeriodicTask.setOnClickListener(this);
        btnCancelTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one_time_task:
                startOneTimeTask();
                break;
            case R.id.btn_periodic_task:
                startPeriodicTask();
                break;
            case R.id.btn_cancel_task:
                cancelPeriodicTask();
                break;
        }
    }

    private void startOneTimeTask() {
        tvStatus.setText(getString(R.string.status));

        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_CITY, edtCity.getText().toString())
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String status = workInfo.getState().name();
                tvStatus.append("\n" + status);
            }
        });
    }

    private void startPeriodicTask() {
        tvStatus.setText(getString(R.string.status));

        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_CITY, edtCity.getText().toString())
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String status = workInfo.getState().name();
                tvStatus.append("\n" + status);
                btnCancelTask.setEnabled(false);
                if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    btnCancelTask.setEnabled(true);
                }
            }
        });
    }

    private void cancelPeriodicTask() {
        WorkManager.getInstance().cancelWorkById(periodicWorkRequest.getId());
    }
}