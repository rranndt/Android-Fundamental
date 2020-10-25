package com.learn.secondsubmissionwithoutnavhost.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.secondsubmissionwithoutnavhost.model.User;
import com.learn.secondsubmissionwithoutnavhost.network.Config;
import com.learn.secondsubmissionwithoutnavhost.network.Const;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<User>> mUsers = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "HomeViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUsers() {
        Call<List<User>> call = Config.getApi().getUser(Const.API_KEY);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                try {
                    if (response.body() != null) {
                        mUsers.postValue(response.body());
                    } else {
                        toastMessage.setValue(response.message());
                        Log.e(TAG, response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }
}
