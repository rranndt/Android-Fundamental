package com.learn.lastsubmission.Ui.Fragment.Followers;

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

public class FollowersFragment extends Fragment {

    private UserMain userMain;
    private List<UserMain> mUser = new ArrayList<>();
    private RecyclerView rvUserFollowers;
    private UserAdapter adapter;
    private ShimmerFrameLayout shimmer;

    private FollowersViewModel viewModel;
    private static final String TAG = "FollowersFragment";

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userMain = getActivity().getIntent().getParcelableExtra(Const.PARCELABLE);
        shimmer = view.findViewById(R.id.shimmerFollowers);

        setRecyclerView(view);
        getFollowers();
    }

    private void setRecyclerView(View view) {
        rvUserFollowers = view.findViewById(R.id.rvUserFollowers);
        rvUserFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserFollowers.setHasFixedSize(true);
        adapter = new UserAdapter(getActivity(), mUser);
        rvUserFollowers.setAdapter(adapter);
        rvUserFollowers.smoothScrollToPosition(0);
    }

    private void getFollowers() {
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);

        viewModel.getToastMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getFollowers().observe(getActivity(), new Observer<List<UserMain>>() {
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
        viewModel.setFollowers(userMain.getLogin());
    }
}