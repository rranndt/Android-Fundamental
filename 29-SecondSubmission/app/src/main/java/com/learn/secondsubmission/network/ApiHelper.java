package com.learn.secondsubmission.network;

import com.learn.secondsubmission.model.SearchUser;
import com.learn.secondsubmission.model.UserDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET("search/users")
    Call<SearchUser> getSearchUser(
            @Query("q") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(
            @Path("username") String username,
            @Header("Authorization") String token
    );
}
