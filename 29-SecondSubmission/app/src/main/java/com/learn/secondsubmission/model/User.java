package com.learn.secondsubmission.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class User {

    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("login")
    private String name;
    @SerializedName("html_url")
    private String url;
    private String type;
    private int id;

}
