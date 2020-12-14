package com.learn.lastsubmission.Ui.Activity.Detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserDetail;
import com.learn.lastsubmission.Network.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DetailViewModel extends ViewModel {

    private final MutableLiveData<UserDetail> mUserDetail = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "DetailViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUserDetail(String username) {
        Call<UserDetail> call = Config.getApi().getUserDetail(username, Const.API_KEY);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
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
            public void onFailure(Call<UserDetail> call, Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<UserDetail> getUserDetail() {
        return mUserDetail;
    }
}
