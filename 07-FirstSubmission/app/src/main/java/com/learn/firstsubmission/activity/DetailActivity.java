package com.learn.firstsubmission.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.firstsubmission.MainActivity;
import com.learn.firstsubmission.R;
import com.learn.firstsubmission.model.User;
import com.learn.firstsubmission.model.UserDetail;
import com.learn.firstsubmission.network.Config;
import com.learn.firstsubmission.network.UrlServer;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView ivAvatar;
    private TextView tvUsername,
            tvToolbarName,
            tvTotFollowers,
            tvTotFollowing,
            tvTotRepository,
            tvType,
            tvLocation,
            tvCompany,
            tvUrl;
    private ImageView ivBackground;
    private ImageButton ibBack,
            ibShare;
    private Group grLocation,
            grCompany;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        user = getIntent().getParcelableExtra(UrlServer.PARCEL);

        initViews();
        initListener();
        getDetailUser();

    }

    private void initViews() {
        ivAvatar = findViewById(R.id.iv_avatar_detail);
        tvUsername = findViewById(R.id.tv_username_detail);
        tvToolbarName = findViewById(R.id.tv_toolbar_name);
        tvTotFollowers = findViewById(R.id.tot_followers);
        tvTotFollowing = findViewById(R.id.tot_following);
        tvTotRepository = findViewById(R.id.tot_repository);
        tvType = findViewById(R.id.tv_type);
        tvLocation = findViewById(R.id.tv_location);
        tvCompany = findViewById(R.id.tv_company);
        tvUrl = findViewById(R.id.tv_url);
        ivBackground = findViewById(R.id.iv_background);
        ibShare = findViewById(R.id.ib_share);
        ibBack = findViewById(R.id.ib_back);

        grLocation = findViewById(R.id.group_location);
        grCompany = findViewById(R.id.group_company);

    }

    private void initListener() {
        ibBack.setOnClickListener(this);
        ibShare.setOnClickListener(this);
    }

    private void getDetailUser() {
        Call<UserDetail> call = Config.getApi().getUserDetail(user.getLogin(), UrlServer.API_KEY);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(@NotNull Call<UserDetail> call, @NotNull Response<UserDetail> response) {
                try {
                    if (response.body() != null) {
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .transform(new BlurTransformation(20))
                                .into(ivBackground);
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .into(ivAvatar);
                        tvToolbarName.setText(response.body().getLogin());
                        tvUsername.setText(response.body().getLogin());
                        tvTotFollowers.setText(String.valueOf(response.body().getFollowers()));
                        tvTotFollowing.setText(String.valueOf(response.body().getFollowing()));
                        tvTotRepository.setText(String.valueOf(response.body().getPublicRepos()));
                        tvType.setText(response.body().getType());

                        if (response.body().getLocation() == null && response.body().getCompany() == null) {
                            grLocation.setVisibility(View.GONE);
                            grCompany.setVisibility(View.GONE);
                        } else if (response.body().getLocation() == null || response.body().getCompany() == null) {
                            if (response.body().getLocation() == null) {
                                grLocation.setVisibility(View.GONE);
                                tvCompany.setText(response.body().getCompany());
                            } else if (response.body().getCompany() == null) {
                                grCompany.setVisibility(View.GONE);
                                tvLocation.setText(response.body().getLocation());
                            }
                        } else {
                            tvLocation.setText(response.body().getLocation());
                            tvCompany.setText(response.body().getCompany());
                        }

                        tvUrl.setText(response.body().getHtmlUrl());
                        tvUrl.setOnClickListener(v -> {
                            Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                            intentUrl.setData(Uri.parse(user.getHtmlUrl()));
                            startActivity(intentUrl);
                        });
                    } else {
                        Log.e("onResponse: ", DetailActivity.class.getSimpleName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserDetail> call, @NotNull Throwable t) {
                Log.e("onFailure: ", DetailActivity.class.getSimpleName());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.ib_share:
                Intent intentSharing = new Intent(Intent.ACTION_SEND);
                intentSharing.setType("text/plain");
                String shareBody = user.getHtmlUrl();
                intentSharing.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intentSharing, "Share with"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}