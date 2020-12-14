package com.learn.lastsubmission.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
@Data
public class UserResponseSearch {

    @SerializedName("total_count")
    private long totalCount;

    @SerializedName("items")
    private List<UserMain> items;
}
