package com.learn.secondsubmissionwithoutnavhost.ui.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmissionwithoutnavhost.model.UserRepo;
import com.learn.secondsubmissionwithoutnavhost.network.Config;
import com.learn.secondsubmissionwithoutnavhost.network.Const;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoViewModel extends ViewModel {

    private final MutableLiveData<List<UserRepo>> mUser = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "RepoViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setRepo(String username) {
        Call<List<UserRepo>> call = Config.getApi().getRepo(username, Const.API_KEY);
        call.enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(@NotNull Call<List<UserRepo>> call, @NotNull Response<List<UserRepo>> response) {
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
            public void onFailure(@NotNull Call<List<UserRepo>> call, @NotNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<List<UserRepo>> getRepo() {
        return mUser;
    }
}
