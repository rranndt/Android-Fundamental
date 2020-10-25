package com.learn.secondsubmissionwithoutnavhost.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.adapter.ViewPagerAdapter;
import com.learn.secondsubmissionwithoutnavhost.model.User;
import com.learn.secondsubmissionwithoutnavhost.model.UserDetail;
import com.learn.secondsubmissionwithoutnavhost.network.Const;
import com.learn.secondsubmissionwithoutnavhost.ui.home.HomeActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvProfileName,
            tvUsername,
            tvType,
            tvLocation,
            tvCompany,
            tvHtmlUrl,
            tvTotFollowers,
            tvTotFollowing,
            tvTotRepository;
    private ImageButton btnBack,
            btnLang;
    private CircleImageView ivAvatar;
    private ShimmerFrameLayout loading;

    private DetailViewModel detailViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        setViewModel();
        setViewPager();
    }

    private void initViews() {
        tvProfileName = findViewById(R.id.tv_profile_name);
        ivAvatar = findViewById(R.id.iv_avatar_profile);
        tvUsername = findViewById(R.id.tv_username_profile);
        tvType = findViewById(R.id.tv_type_profile);
        tvLocation = findViewById(R.id.tv_location_profile);
        tvCompany = findViewById(R.id.tv_company_profile);
        tvHtmlUrl = findViewById(R.id.tv_html_url_profile);
        tvTotFollowers = findViewById(R.id.tv_tot_followers);
        tvTotFollowing = findViewById(R.id.tv_tot_following);
        tvTotRepository = findViewById(R.id.tv_tot_repos);
        loading = findViewById(R.id.shimmer_detail);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        btnLang = findViewById(R.id.btn_lang);
        btnLang.setOnClickListener(this);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
    }

    private void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewModel() {
        loading.setVisibility(View.VISIBLE);
        username = getIntent().getStringExtra(Const.BUNDLE);

        detailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        detailViewModel.getToastMessage().observe(this, message -> {
            Toast.makeText(this, getString(R.string.something_unexpected), Toast.LENGTH_SHORT).show();
        });
        detailViewModel.getUserDetail().observe(this, userDetail -> {
            try {
                loading.setVisibility(View.GONE);
                tvProfileName.setText(userDetail.getLogin());

                Glide.with(getApplicationContext())
                        .load(userDetail.getAvatarUrl())
                        .apply(new RequestOptions().override(85, 85))
                        .into(ivAvatar);
                tvUsername.setText(userDetail.getLogin());
                tvType.setText(userDetail.getType());
                tvTotFollowers.setText(String.valueOf(userDetail.getFollowers()));
                tvTotFollowing.setText(String.valueOf(userDetail.getFollowing()));
                tvTotRepository.setText(String.valueOf(userDetail.getPublicRepos()));

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
                tvHtmlUrl.setOnClickListener(v -> {
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                    intentUrl.setData(Uri.parse(userDetail.getHtmlUrl()));
                    startActivity(intentUrl);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        detailViewModel.setUserDetail(username);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            startActivity(new Intent(DetailActivity.this, HomeActivity.class));
        } else if (v.getId() == R.id.btn_lang) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));

        }
    }
}