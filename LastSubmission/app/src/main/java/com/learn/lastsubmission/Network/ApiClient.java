package com.learn.lastsubmission.Network;

import com.learn.lastsubmission.Model.UserDetail;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.Model.UserResponseSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public interface ApiClient {

    @GET("users")
    Call<List<UserMain>> getUserMain(
            @Header("Authorization") String token
    );

    @GET("search/users")
    Call<UserResponseSearch> getUserSearch(
            @Query("q") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(
            @Path("username") String username,
            @Header("Authorization") String token

    );

    @GET("users/{username}/followers")
    Call<List<UserMain>> getUserFollowers(
            @Path("username") String username,
            @Header("Authorization") String token

    );

    @GET("users/{username}/following")
    Call<List<UserMain>> getUserFollowing(
            @Path("username") String username,
            @Header("Authorization") String token
    );
}
