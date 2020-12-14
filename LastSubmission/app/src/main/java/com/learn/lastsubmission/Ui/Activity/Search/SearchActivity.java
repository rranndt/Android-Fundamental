package com.learn.lastsubmission.Ui.Activity.Search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.lastsubmission.Adapter.UserAdapter;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.Model.UserResponseSearch;
import com.learn.lastsubmission.Network.Config;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Ui.Activity.Favorite.FavoriteActivity;
import com.learn.lastsubmission.Ui.Activity.Home.MainActivity;
import com.learn.lastsubmission.Ui.Activity.Setting.SettingActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBackSearch, btnFavoriteSearch, btnSettingSearch;
    private TextView tvSearchUsername, tvNoDataSearch;
    private RecyclerView rvUser;
    private ShimmerFrameLayout shimmer;

    private final List<UserMain> list = new ArrayList<>();
    private SearchViewModel viewModel;
    private UserAdapter adapter;

    private String message = "";

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setRecyclerView();
        setViewModel();
    }

    private void initViews() {
        String text = getIntent().getStringExtra(Const.PARCELABLE);

        shimmer = findViewById(R.id.shimmerMain);
        rvUser = findViewById(R.id.rvUser);
        btnBackSearch = findViewById(R.id.btnBackSearch);
        btnFavoriteSearch = findViewById(R.id.btnFavoriteSearch);
        btnSettingSearch = findViewById(R.id.btnSettingSearch);
        tvSearchUsername = findViewById(R.id.tvSearchUsername);
        tvNoDataSearch = findViewById(R.id.tvNoDataSearch);

        btnBackSearch.setOnClickListener(this);
        btnFavoriteSearch.setOnClickListener(this);
        btnSettingSearch.setOnClickListener(this);
        tvSearchUsername.setOnClickListener(this);

        tvSearchUsername.setText(text);
    }

    private void setRecyclerView() {
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);
        rvUser.smoothScrollToPosition(0);
        adapter = new UserAdapter(this, list);
        rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(SearchActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUserSearch().observe(this, new Observer<UserResponseSearch>() {
            @Override
            public void onChanged(UserResponseSearch userResponseSearch) {
                try {
                    shimmer.setVisibility(View.GONE);
                    if (userResponseSearch.getTotalCount() > 0) {
                        adapter.setData(userResponseSearch.getItems());
                    } else {
                        adapter.clearList(userResponseSearch.getItems());
                        tvNoDataSearch.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewModel.setUserSearch(getIntent().getStringExtra(Const.PARCELABLE));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackSearch:
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnFavoriteSearch:
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnSettingSearch:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}