package com.learn.secondsubmission.ui.userdetail;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmission.model.UserDetail;
import com.learn.secondsubmission.network.Const;
import com.learn.secondsubmission.network.RetrofitService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewModel extends ViewModel {

    private final MutableLiveData<UserDetail> mUserDetail = new MutableLiveData<>();

    public void setUserDetail(String username) {
        Call<UserDetail> call = RetrofitService.getApi().getUserDetail(username, Const.TOKEN);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(@NotNull Call<UserDetail> call, @NotNull Response<UserDetail> response) {
                try {
                    if (response.body() != null) {
                        UserDetail userDetail = response.body();
                        mUserDetail.setValue(userDetail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserDetail> call, @NotNull Throwable t) {
                Log.e("UserDetailViewModel: ", t.getMessage());
                mUserDetail.setValue(null);
            }
        });
    }

    public LiveData<UserDetail> getUserDetail() {
        return mUserDetail;
    }

    @Override
    protected void onCleared() {
        mUserDetail.setValue(null);
        super.onCleared();
    }
}
