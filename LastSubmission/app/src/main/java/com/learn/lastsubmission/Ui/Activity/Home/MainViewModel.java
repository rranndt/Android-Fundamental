package com.learn.lastsubmission.Ui.Activity.Home;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.lastsubmission.Adapter.UserAdapter;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.Network.Config;
import com.learn.lastsubmission.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<UserMain>> mUser = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private static final String TAG = "HomeViewModel";

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setUser() {
        Call<List<UserMain>> call = Config.getApi().getUserMain(Const.API_KEY);
        call.enqueue(new Callback<List<UserMain>>() {
            @Override
            public void onResponse(Call<List<UserMain>> call, Response<List<UserMain>> response) {
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
            public void onFailure(Call<List<UserMain>> call, Throwable t) {
                toastMessage.setValue(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public LiveData<List<UserMain>> getUser() {
        return mUser;
    }
}
