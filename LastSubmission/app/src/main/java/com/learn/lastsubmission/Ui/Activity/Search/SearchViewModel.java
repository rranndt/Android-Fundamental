package com.learn.lastsubmission.Ui.Activity.Search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserResponseSearch;
import com.learn.lastsubmission.Network.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class SearchViewModel extends ViewModel {

    private final MutableLiveData <UserResponseSearch> mUser = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "SearchViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUserSearch(String username) {
        Call<UserResponseSearch> call = Config.getApi().getUserSearch(username, Const.API_KEY);
        call.enqueue(new Callback<UserResponseSearch>() {
            @Override
            public void onResponse(Call<UserResponseSearch> call, Response<UserResponseSearch> response) {
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
            public void onFailure(Call<UserResponseSearch> call, Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<UserResponseSearch> getUserSearch() {
        return mUser;
    }
}
