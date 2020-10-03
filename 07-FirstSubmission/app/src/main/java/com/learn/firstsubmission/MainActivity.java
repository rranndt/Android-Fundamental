package com.learn.firstsubmission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.firstsubmission.adapter.UserAdapter;
import com.learn.firstsubmission.model.User;
import com.learn.firstsubmission.network.Config;
import com.learn.firstsubmission.network.UrlServer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmer;

    private Toast backToast;
    private long secondBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecycler();
        getData();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_list);
//        shimmer = findViewById(R.id.shimmer_container);
    }

    private void setupRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Call<List<User>> call = Config.getApi().getUser(UrlServer.API_KEY);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                try {
                    if (response.body() != null) {
                        adapter = new UserAdapter(response.body(), MainActivity.this);
                        recyclerView.setAdapter(adapter);
//                        shimmer.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Log.e("onResponse: ", MainActivity.class.getSimpleName());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                Log.e("onFailure: ", MainActivity.class.getSimpleName());
            }
        });
    }
}