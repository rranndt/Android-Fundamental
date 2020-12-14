package com.learn.lastsubmission.Ui.Activity.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.lastsubmission.Adapter.UserAdapter;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Ui.Activity.Favorite.FavoriteActivity;
import com.learn.lastsubmission.Ui.Activity.Search.SearchActivity;
import com.learn.lastsubmission.Ui.Activity.Setting.SettingActivity;
import com.learn.lastsubmission.Database.GithubHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUser;
    private ShimmerFrameLayout shimmer;

    private SearchView searchView;
    private final List<UserMain> list = new ArrayList<>();
    private GithubHelper githubHelper;
    private String message;
    private MainViewModel viewMode;
    private UserAdapter adapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setRecyclerView();
        getSearchUser();
        setViewModel();
    }

    private void initViews() {
        shimmer = findViewById(R.id.shimmerMain);
        searchView = findViewById(R.id.search_view);
        rvUser = findViewById(R.id.rvUser);
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
        viewMode = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        viewMode.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        viewMode.getUser().observe(this, new Observer<List<UserMain>>() {
            @Override
            public void onChanged(List<UserMain> userMainList) {
                if (userMainList != null) {
                    shimmer.setVisibility(View.GONE);
                    adapter.setData(userMainList);
                } else {
                    adapter.clearList(null);
                }
            }
        });
        viewMode.setUser();
    }

    private void getSearchUser() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getInputText(query);

                Intent intentSearch = new Intent(getApplicationContext(), SearchActivity.class);
                intentSearch.putExtra(Const.PARCELABLE, query);
                intentSearch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentSearch);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getInputText(String searchText) {
        Intent intentSearchText = new Intent(getApplicationContext(), SearchActivity.class);
        intentSearchText.putExtra(Const.PARCELABLE, searchText);
        startActivity(intentSearchText);
    }

    public void btnFavoriteMain(View view) {
        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnSettingMain(View view) {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}