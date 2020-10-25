package com.learn.secondsubmissionwithoutnavhost.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch {

    @SerializedName("total_count")
    private long totalCount;
    private List<User> items;
}
