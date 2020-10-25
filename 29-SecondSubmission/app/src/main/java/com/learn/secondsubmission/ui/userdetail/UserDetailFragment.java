package com.learn.secondsubmission.ui.userdetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.learn.secondsubmission.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailFragment extends Fragment implements View.OnClickListener {

    private TextView tvProfileName,
            tvUsername,
            tvType,
            tvLocation,
            tvCompany,
            tvHtmlUrl,
            tvTotFollowers,
            tvTotFollowing,
            tvTotRepository;
    private ImageButton btnBack;
    private CircleImageView ivAvatar;
    private LottieAnimationView loading;

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private NestedScrollView nestedScrollView;

    private UserDetailViewModel viewModel;

    private String username;
    private static final String TAG = "UserDetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        initViews(view);

        try {
            if (getArguments() != null) {
                username = UserDetailFragmentArgs.fromBundle(getArguments()).getUser();
                Log.d(TAG, "profile: ");
                tvProfileName.setText(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getUserDetail();
    }

    private void initViews(View view) {
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvUsername = view.findViewById(R.id.tv_username);
        tvType = view.findViewById(R.id.tv_type);
        tvLocation = view.findViewById(R.id.tv_location);
        tvCompany = view.findViewById(R.id.tv_company);
        tvHtmlUrl = view.findViewById(R.id.tv_html_url);
        tvTotFollowers = view.findViewById(R.id.tv_tot_followers);
        tvTotFollowing = view.findViewById(R.id.tv_tot_following);
        tvTotRepository = view.findViewById(R.id.tv_tot_repos);
        btnBack = view.findViewById(R.id.btn_back);

        loading = view.findViewById(R.id.user_detail_loading);

        btnBack.setOnClickListener(this);
    }

    private void getUserDetail() {
        Log.d(TAG, "getUserDetail: ");
        loading.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()),
                    new ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel.class);
            viewModel.getUserDetail().observe(getViewLifecycleOwner(), userDetail -> {
                Log.d(TAG, "onChanged: ");
                if (getViewLifecycleOwner().getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                    try {
                        if (getContext() != null) {

                            Glide.with(getContext())
                                    .load(userDetail.getAvatarUrl())
                                    .error(R.drawable.img_blank)
                                    .apply(new RequestOptions().override(85, 85))
                                    .into(ivAvatar);
                            tvUsername.setText(userDetail.getUsername());
                            tvType.setText(userDetail.getType());
                            tvLocation.setText(userDetail.getLocation());
                            tvCompany.setText(userDetail.getCompany());
                            tvHtmlUrl.setText(userDetail.getHtmlUrl());

                            tvTotFollowers.setText(String.valueOf(userDetail.getFollowers()));
                            tvTotFollowing.setText(String.valueOf(userDetail.getFollowing()));
                            tvTotRepository.setText(String.valueOf(userDetail.getRepos()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                loading.setVisibility(View.GONE);
            });
            viewModel.setUserDetail(username);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            Navigation.findNavController(v).navigate(R.id.action_userDetailFragment_to_homeFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setUserDetail(null);
//        Objects.requireNonNull(getActivity()).getViewModelStore().clear();
    }
}