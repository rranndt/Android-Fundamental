package com.learn.lastsubmission.Ui.Activity.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;
import com.learn.lastsubmission.Adapter.ViewPagerAdapter;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Ui.Activity.Favorite.FavoriteActivity;
import com.learn.lastsubmission.Model.UserDetail;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Ui.Activity.Setting.SettingActivity;
import com.learn.lastsubmission.Database.DatabaseContract;
import com.learn.lastsubmission.Database.DatabaseHelper;
import com.learn.lastsubmission.Database.GithubHelper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.AVATAR;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.CONTENT_URI;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.ID;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.TABLE_NAME;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.URL;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.USERNAME;

public class DetailActivity extends AppCompatActivity {

    private CircleImageView ivAvatar;
    private TextView tvProfileName, tvUsername, tvType, tvLocation, tvCompany, tvHtmlUrl, tvTotFollowers, tvTotFollowing, tvTotRepos;
    private MaterialFavoriteButton materialFavoriteButton;
    private ConstraintLayout clGroup;
    private ShimmerFrameLayout shimmerDetail;

    private UserMain userMain;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GithubHelper githubHelper;
    private ArrayList<UserMain> userMainArrayList = new ArrayList<>();
    private String message;
    private boolean btn = true;

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        userMain = getIntent().getParcelableExtra(Const.PARCELABLE);
        githubHelper = GithubHelper.getInstance(getApplicationContext());

        initViews();
        setViewPager();
        setFavButton();
        setViewModel();
    }

    private void initViews() {
        ivAvatar = findViewById(R.id.ivAvatarDetail);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvUsername = findViewById(R.id.tvUsernameDetail);
        tvType = findViewById(R.id.tvTypeDetail);
        tvLocation = findViewById(R.id.tvLocationDetail);
        tvCompany = findViewById(R.id.tvCompanyDetail);
        tvHtmlUrl = findViewById(R.id.tvHtmlUrlDetail);
        tvTotFollowers = findViewById(R.id.tvTotFollowers);
        tvTotFollowing = findViewById(R.id.tvTotFollowing);
        tvTotRepos = findViewById(R.id.tvTotRepos);
        clGroup = findViewById(R.id.clGroup);
        shimmerDetail = findViewById(R.id.shimmerDetail);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        materialFavoriteButton = findViewById(R.id.mfb_favorite);
    }

    private void setViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewModel() {
        DetailViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(DetailActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUserDetail().observe(this, new Observer<UserDetail>() {
            @Override
            public void onChanged(UserDetail userDetail) {
                try {
                    shimmerDetail.setVisibility(View.GONE);
                    clGroup.setVisibility(View.VISIBLE);

                    tvProfileName.setText(userDetail.getLogin());
                    Glide.with(getApplicationContext())
                            .load(userDetail.getAvatarUrl())
                            .apply(new RequestOptions().override(60, 60))
                            .into(ivAvatar);
                    tvUsername.setText(userDetail.getLogin());
                    tvType.setText(userDetail.getType());
                    tvTotFollowers.setText(String.valueOf(userDetail.getFollowers()));
                    tvTotFollowing.setText(String.valueOf(userDetail.getFollowing()));
                    tvTotRepos.setText(String.valueOf(userDetail.getPublicRepos()));

                    if (userDetail.getLocation() == null && userDetail.getCompany() == null) {
                        tvLocation.setVisibility(View.GONE);
                        tvCompany.setVisibility(View.GONE);
                    } else if (userDetail.getLocation() == null || userDetail.getCompany() == null) {
                        if (userDetail.getLocation() == null) {
                            tvLocation.setVisibility(View.GONE);
                            tvCompany.setText(userDetail.getCompany());
                        } else if (userDetail.getCompany() == null) {
                            tvCompany.setVisibility(View.GONE);
                            tvLocation.setText(userDetail.getLocation());
                        }
                    } else {
                        tvCompany.setText(userDetail.getCompany());
                        tvLocation.setText(userDetail.getLocation());
                    }

                    tvHtmlUrl.setText(userDetail.getHtmlUrl());
                    tvHtmlUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                            intentUrl.setData(Uri.parse(userDetail.getHtmlUrl()));
                            startActivity(intentUrl);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewModel.setUserDetail(userMain.getLogin());
    }

    private void setFavButton() {
        if (duplicated(userMain.getLogin())) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        addToFavorite();
                        Toast.makeText(DetailActivity.this, getString(R.string.add_to_favorite), Toast.LENGTH_SHORT).show();
                    } else {
                        removeFromFavorite();
                        Toast.makeText(DetailActivity.this, getString(R.string.remove_from_favorite), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        addToFavorite();
                        Toast.makeText(DetailActivity.this, getString(R.string.add_to_favorite), Toast.LENGTH_SHORT).show();
                    } else {
                        removeFromFavorite();
                        Toast.makeText(DetailActivity.this, getString(R.string.remove_from_favorite), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addToFavorite() {
        userMainArrayList = githubHelper.getDataUser();
        if (userMainArrayList != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, userMain.getId());
            contentValues.put(USERNAME, userMain.getLogin());
            contentValues.put(URL, userMain.getHtmlUrl());
            contentValues.put(AVATAR, userMain.getAvatarUrl());

            getContentResolver().insert(CONTENT_URI, contentValues);
        }
    }

    private void removeFromFavorite() {
        userMainArrayList = githubHelper.getDataUser();
        githubHelper.deleteById(String.valueOf(userMain.getId()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnBack(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnSettingDetail(View view) {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnFavoriteDetail(View view) {
        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private boolean duplicated(String user) {
        String max = "1";
        String[] args = {user};
        String username = DatabaseContract.GithubColumn.USERNAME + "=?";
        githubHelper = new GithubHelper(this);
        githubHelper.open();

        DatabaseHelper databasehelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = databasehelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                null,
                username,
                args,
                null,
                null,
                null,
                max);

        boolean duplicated = (cursor.getCount() > 0);
        cursor.close();
        return duplicated;
    }

}