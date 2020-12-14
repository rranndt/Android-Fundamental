package com.learn.lastsubmission.Model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
@Data
public class UserDetail {

    @SerializedName("id")
    private int id;

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

    @SerializedName("public_repos")
    private int publicRepos;

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

    @SerializedName("followers")
    private int followers;

    @SerializedName("following")
    private int following;
}
