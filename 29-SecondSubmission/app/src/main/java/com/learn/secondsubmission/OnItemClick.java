package com.learn.secondsubmission;

import android.view.View;

import com.learn.secondsubmission.model.User;

public interface OnItemClick {
    void onItemClicked(User user, int position, View view);
}
