package com.learn.secondsubmissionwithoutnavhost.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.adapter.ListUserAdapter;
import com.learn.secondsubmissionwithoutnavhost.model.User;
import com.learn.secondsubmissionwithoutnavhost.model.UserSearch;
import com.learn.secondsubmissionwithoutnavhost.network.Const;
import com.learn.secondsubmissionwithoutnavhost.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ListUserAdapter adapter;
    private LottieAnimationView loading;
    private HomeViewModel homeViewModel;
    private SearchViewModel searchViewModel;
    private ConstraintLayout bgUserNotFound;

    private final List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setRecyclerView();
        setViewModel();
        setSearchView();
    }

    private void initViews() {
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view);
        loading = findViewById(R.id.loading);
        bgUserNotFound = findViewById(R.id.include_user_not_found);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new ListUserAdapter(list, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback((user, position) -> {
            Intent intentDetail = new Intent(HomeActivity.this, DetailActivity.class);
            intentDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle mBundle = new Bundle();
            mBundle.putString(Const.BUNDLE, user.getName());
            intentDetail.putExtras(mBundle);
            startActivity(intentDetail);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void setViewModel() {
        loading.setVisibility(View.VISIBLE);

        // HomeViewModel
        homeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        // Get Error Toast
        homeViewModel.getToastMessage().observe(this,
                message -> {
                    Toast.makeText(this, getString(R.string.something_unexpected), Toast.LENGTH_SHORT).show();
                });
        homeViewModel.getUsers().observe(this, users -> {
            if (users != null) {
                loading.setVisibility(View.GONE);
                adapter.setData(users);
            } else {
                adapter.clearList(null);
            }
        });
        homeViewModel.setUsers();

        // SearchViewModel
        searchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        // Get Error Toast
        searchViewModel.getToastMessage().observe(this, s ->
                Toast.makeText(HomeActivity.this, getString(R.string.something_unexpected), Toast.LENGTH_SHORT).show()
        );
        searchViewModel.getUserSearch().observe(this, userSearch -> {
            if (userSearch.getTotalCount() > 0) {
                adapter.setData(userSearch.getItems());
                loading.setVisibility(View.GONE);
            } else {
                adapter.clearList(userSearch.getItems());
                loading.setVisibility(View.GONE);
                bgUserNotFound.setVisibility(View.VISIBLE);
            }

            // Hide keyboard after show data
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loading.setVisibility(View.VISIBLE);
                searchViewModel.setUserSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void btnLang(View view) {
        startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
    }
}