package com.learn.lastsubmission.Ui.Fragment.Following;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.lastsubmission.Adapter.UserAdapter;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.R;

import java.util.ArrayList;
import java.util.List;

public class FollowingFragment extends Fragment {

    private UserMain userMain;
    private List<UserMain> mUser = new ArrayList<>();
    private RecyclerView rvUserFollowing;
    private ShimmerFrameLayout shimmer;
    private UserAdapter adapter;
    private FollowingViewModel viewModel;

    private static final String TAG = "FollowingFragment";

    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userMain = getActivity().getIntent().getParcelableExtra(Const.PARCELABLE);
        shimmer = view.findViewById(R.id.shimmerFollowing);

        setRecyclerView(view);
        getFollowing();
    }

    private void setRecyclerView(View view) {
        rvUserFollowing = view.findViewById(R.id.rvUserFollowing);
        rvUserFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserFollowing.setHasFixedSize(true);
        adapter = new UserAdapter(getActivity(), mUser);
        rvUserFollowing.setAdapter(adapter);
        rvUserFollowing.smoothScrollToPosition(0);
    }

    private void getFollowing() {
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        viewModel.getToastMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getFollowing().observe(getActivity(), new Observer<List<UserMain>>() {
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
        viewModel.setFollowing(userMain.getLogin());
    }
}