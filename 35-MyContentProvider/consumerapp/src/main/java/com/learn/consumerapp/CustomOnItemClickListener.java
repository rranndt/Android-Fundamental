package com.learn.consumerapp;

import android.view.View;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onitemClicked(v, position);
    }

    public interface OnItemClickCallback {
        void onitemClicked(View view, int position);
    }
}
