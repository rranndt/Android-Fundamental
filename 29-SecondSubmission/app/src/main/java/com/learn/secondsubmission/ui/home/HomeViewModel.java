package com.learn.secondsubmission.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmission.adapter.ListUserSearchAdapter;
import com.learn.secondsubmission.model.SearchUser;
import com.learn.secondsubmission.network.Const;
import com.learn.secondsubmission.network.RetrofitService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<SearchUser> mUser = new MutableLiveData<>();
    private static final String TAG = "HomeViewModel";

    public void setSearchUser(String username) {
        Call<SearchUser> call = RetrofitService.getApi().getSearchUser(username, Const.TOKEN);
        call.enqueue(new Callback<SearchUser>() {
            @Override
            public void onResponse(@NotNull Call<SearchUser> call, @NotNull Response<SearchUser> response) {
                try {
                    if (response.body() != null) {
                        mUser.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SearchUser> call, @NotNull Throwable t) {
            }
        });
    }

    public LiveData<SearchUser> getSearchUser() {
        return mUser;
    }
}
