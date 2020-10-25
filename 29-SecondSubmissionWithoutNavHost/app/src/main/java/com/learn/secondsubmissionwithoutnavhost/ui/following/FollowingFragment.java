package com.learn.secondsubmissionwithoutnavhost.ui.following;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.adapter.ListUserAdapter;
import com.learn.secondsubmissionwithoutnavhost.model.User;
import com.learn.secondsubmissionwithoutnavhost.network.Const;
import com.learn.secondsubmissionwithoutnavhost.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListUserAdapter adapter;
    private final List<User> mUser = new ArrayList<>();
    private FollowingViewModel followingViewModel;
    private ShimmerFrameLayout shimmerLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setRecyclerView();
        getFollowingViewModel();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_following);
        shimmerLoading = view.findViewById(R.id.shimmer);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new ListUserAdapter(mUser, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback((user, posistion) -> {
            Intent intentDetail = new Intent(getContext(), DetailActivity.class);
            intentDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle mBundle = new Bundle();
            mBundle.putString(Const.BUNDLE, user.getName());
            intentDetail.putExtras(mBundle);
            startActivity(intentDetail);
        });
    }

    private void getFollowingViewModel() {
        // Get Data
        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Const.BUNDLE);
        followingViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);
        // Toast Error Message
        followingViewModel.getToastMessage().observe(getActivity(), s ->
                Toast.makeText(getActivity(), getString(R.string.something_unexpected), Toast.LENGTH_SHORT).show());
        followingViewModel.getFollowing().observe(getActivity(), users -> {
            if (users != null) {
                shimmerLoading.setVisibility(View.GONE);
                adapter.setData(users);
            } else {
                adapter.clearList(null);
            }
        });
        followingViewModel.setFollowing(username);
    }
}