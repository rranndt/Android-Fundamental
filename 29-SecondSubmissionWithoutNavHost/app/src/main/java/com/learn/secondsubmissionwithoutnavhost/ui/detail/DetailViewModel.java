package com.learn.secondsubmissionwithoutnavhost.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmissionwithoutnavhost.model.UserDetail;
import com.learn.secondsubmissionwithoutnavhost.network.Config;
import com.learn.secondsubmissionwithoutnavhost.network.Const;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private final MutableLiveData<UserDetail> mUserDetail = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage =new MutableLiveData<>();
    private static final String TAG = "DetailViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUserDetail(String username) {
        Call<UserDetail> call = Config.getApi().getUserDetail(username, Const.API_KEY);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(@NotNull Call<UserDetail> call, @NotNull Response<UserDetail> response) {
                try {
                    if (response.body() != null) {
                        mUserDetail.postValue(response.body());
                    } else {
                        toastMessage.setValue(response.message());
                        Log.e(TAG, response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserDetail> call, @NotNull Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<UserDetail> getUserDetail() {
        return mUserDetail;
    }
}
