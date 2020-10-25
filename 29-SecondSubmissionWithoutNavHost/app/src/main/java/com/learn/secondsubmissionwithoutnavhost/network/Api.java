package com.learn.secondsubmissionwithoutnavhost.network;

import com.learn.secondsubmissionwithoutnavhost.model.User;
import com.learn.secondsubmissionwithoutnavhost.model.UserDetail;
import com.learn.secondsubmissionwithoutnavhost.model.UserRepo;
import com.learn.secondsubmissionwithoutnavhost.model.UserSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("search/users")
    Call<UserSearch> getUserSearch(
            @Query("q") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}/followers")
    Call<List<User>> getFollowers(
            @Path("username") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}/following")
    Call<List<User>> getFollowing(
            @Path("username") String username,
            @Header("Authorization") String token
    );

    @GET("users/{username}/repos")
    Call<List<UserRepo>> getRepo(
            @Path("username") String username,
            @Header("Authorization") String token
    );
}
