package com.learn.secondsubmissionwithoutnavhost.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmissionwithoutnavhost.model.UserSearch;
import com.learn.secondsubmissionwithoutnavhost.network.Config;
import com.learn.secondsubmissionwithoutnavhost.network.Const;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<UserSearch> mUser = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "SearchViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUserSearch(String username) {
        Call<UserSearch> call = Config.getApi().getUserSearch(username, Const.API_KEY);
        call.enqueue(new Callback<UserSearch>() {
            @Override
            public void onResponse(@NotNull Call<UserSearch> call, @NotNull Response<UserSearch> response) {
                try {
                    if (response.body() != null) {
                        mUser.postValue(response.body());
                    } else {
                        toastMessage.setValue(response.message());
                        Log.e(TAG, response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserSearch> call, @NotNull Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<UserSearch> getUserSearch() {
        return mUser;
    }
}
