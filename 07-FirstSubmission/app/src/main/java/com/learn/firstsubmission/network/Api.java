package com.learn.firstsubmission.network;

import com.learn.firstsubmission.model.User;
import com.learn.firstsubmission.model.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Api {

    @GET("users")
    Call<List<User>> getUser(
            @Header("Authorization") String token
    );

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(
            @Path("username") String username,
            @Header("Authorization") String token
    );
}
