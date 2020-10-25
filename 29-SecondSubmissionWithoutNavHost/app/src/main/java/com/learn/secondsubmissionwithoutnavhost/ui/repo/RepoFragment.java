package com.learn.secondsubmissionwithoutnavhost.ui.repo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.adapter.UserRepoAdapter;
import com.learn.secondsubmissionwithoutnavhost.model.UserRepo;
import com.learn.secondsubmissionwithoutnavhost.network.Const;

import java.util.ArrayList;
import java.util.List;

public class RepoFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserRepoAdapter adapter;
    private final List<UserRepo> mUser = new ArrayList<>();
    private RepoViewModel repoViewModel;
    private ShimmerFrameLayout loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setRecyclerView();
        setRepoViewModel();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_repo);
        loading = view.findViewById(R.id.shimmer_repo);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new UserRepoAdapter(mUser, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRepoViewModel() {
        String username = getActivity().getIntent().getStringExtra(Const.BUNDLE);
        repoViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(RepoViewModel.class);
        // Get Error Toast
        repoViewModel.getToastMessage().observe(getActivity(), s -> {
            Toast.makeText(getActivity(), getString(R.string.something_unexpected), Toast.LENGTH_SHORT).show();
        });
        repoViewModel.getRepo().observe(getActivity(), userRepos -> {
            if (userRepos != null) {
                loading.setVisibility(View.GONE);
                adapter.setData(userRepos);
            }
        });
        repoViewModel.setRepo(username);
    }
}