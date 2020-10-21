package com.learn.secondsubmission.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.learn.secondsubmission.OnItemClick;
import com.learn.secondsubmission.R;
import com.learn.secondsubmission.adapter.ListUserSearchAdapter;
import com.learn.secondsubmission.model.SearchUser;
import com.learn.secondsubmission.model.User;
import com.learn.secondsubmission.network.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ConstraintLayout bgDashboardHome,
            bgUserNotFound;
    private LottieAnimationView lottieAnimationView;
    private HomeViewModel viewModel;
    private ShimmerFrameLayout shimmerFrameLayout;

    private List<User> list = new ArrayList<>();
    private ListUserSearchAdapter listUserSearchAdapter;
    private static final String TAG = "HomeFragment";

    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();

        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.rv_github_user);
        lottieAnimationView = view.findViewById(R.id.loading_bar);
        bgDashboardHome = view.findViewById(R.id.bg_dashboard_home);
        bgUserNotFound = view.findViewById(R.id.bg_user_not_found);

        setRecyclerView();
        setSearchView();
        searchUserViewModel();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        listUserSearchAdapter = new ListUserSearchAdapter(list, getActivity());
        recyclerView.setAdapter(listUserSearchAdapter);
        listUserSearchAdapter.notifyDataSetChanged();

        listUserSearchAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClicked(User user, int position, View view) {
                Toast.makeText(getContext(), user.getName(), Toast.LENGTH_SHORT).show();

                HomeFragmentDirections.ActionHomeFragmentToUserDetailFragment toUserDetailFragment = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment();
                toUserDetailFragment.setUser(user.getName());
                Navigation.findNavController(view).navigate(toUserDetailFragment);
            }
        });
    }

    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                getSearchUser(query);
                lottieAnimationView.setVisibility(View.VISIBLE);
                bgDashboardHome.setVisibility(View.GONE);
                viewModel.setSearchUser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

//    private void getSearchUser(String username) {
//        lottieAnimationView.setVisibility(View.VISIBLE);
//        constraintLayout.setVisibility(View.GONE);
//        Call<SearchUser> call = RetrofitService.getApi().getSearchUser(username, Const.TOKEN);
//        call.enqueue(new Callback<SearchUser>() {
//            @Override
//            public void onResponse(@NotNull Call<SearchUser> call, @NotNull Response<SearchUser> response) {
//                try {
//                    if (response.body() != null) {
//                        listUserSearchAdapter = new ListUserSearchAdapter(response.body().getItems(), getActivity());
//                        recyclerView.setAdapter(listUserSearchAdapter);
//                        constraintLayout.setVisibility(View.GONE);
//                        lottieAnimationView.setVisibility(View.GONE);
//
//                        // Hide Keyboard
//                        if (Objects.requireNonNull(getActivity()).getCurrentFocus() != null) {
//                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<SearchUser> call, @NotNull Throwable t) {
//                Log.d(TAG, "onFailure: ");
//            }
//        });
//    }

    private void searchUserViewModel() {
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()),
                new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        viewModel.getSearchUser().observe(getActivity(), new Observer<SearchUser>() {
            @Override
            public void onChanged(SearchUser searchUser) {

                if (searchUser.getTotalCount() > 0) {
                    listUserSearchAdapter.setData(searchUser.getItems());
                    bgDashboardHome.setVisibility(View.GONE);
                    bgUserNotFound.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.GONE);
                } else {
                    listUserSearchAdapter.clearList(searchUser.getItems());
                    bgUserNotFound.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.GONE);
                }

                // Hide Keyboard
                if (Objects.requireNonNull(getActivity()).getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }
}