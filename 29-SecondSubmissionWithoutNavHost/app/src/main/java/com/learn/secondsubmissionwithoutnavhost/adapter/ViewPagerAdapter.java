package com.learn.secondsubmissionwithoutnavhost.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.ui.followers.FollowersFragment;
import com.learn.secondsubmissionwithoutnavhost.ui.following.FollowingFragment;
import com.learn.secondsubmissionwithoutnavhost.ui.repo.RepoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
    }

    private final int[] TAB_TITLE = new int[]{
            R.string.followers,
            R.string.following,
            R.string.repository
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowersFragment();
                break;
            case 1:
                fragment = new FollowingFragment();
                break;
            case 2:
                fragment = new RepoFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLE[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
