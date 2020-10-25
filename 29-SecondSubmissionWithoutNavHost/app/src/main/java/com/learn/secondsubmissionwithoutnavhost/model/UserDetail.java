package com.learn.secondsubmissionwithoutnavhost.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserDetail {

    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("url")
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;
    @SerializedName("repos_url")
    private String reposUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private String company;
    @SerializedName("blog")
    private String blog;
    @SerializedName("location")
    private String location;
    @SerializedName("email")
    private String email;
    @SerializedName("public_repos")
    private int publicRepos;
    @SerializedName("followers")
    private int followers;
    @SerializedName("following")
    private int following;
}
