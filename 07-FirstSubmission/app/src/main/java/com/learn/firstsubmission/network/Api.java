package com.learn.firstsubmission.network;

import com.learn.firstsubmission.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    @GET("users")
    Call<List<User>> getUser(
            @Header("Authorization") String token
    );
}
