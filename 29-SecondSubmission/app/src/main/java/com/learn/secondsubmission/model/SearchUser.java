package com.learn.secondsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUser {

    @SerializedName("total_count")
    private long totalCount;

    private List<User> items;
}
