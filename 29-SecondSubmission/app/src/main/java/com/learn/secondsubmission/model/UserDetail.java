package com.learn.secondsubmission.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserDetail {

    @SerializedName("login")
    private String username;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;
    private String type;
    private String blog;
    private String location;
    private String company;
    @SerializedName("public_repos")
    private int repos;
    private int followers;
    private int following;
}
