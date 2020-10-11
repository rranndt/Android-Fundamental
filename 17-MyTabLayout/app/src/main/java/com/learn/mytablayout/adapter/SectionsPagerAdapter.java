package com.learn.mytablayout.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.learn.mytablayout.R;
import com.learn.mytablayout.ui.home.HomeFragment;
import com.learn.mytablayout.ui.profile.ProfileFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[] {
            R.string.tab_text1,
            R.string.tab_text2,
            R.string.tab_text3
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:
//                fragment = new HomeFragment();
//                break;
//            case 1:
//                fragment = new ProfileFragment();
//                break;
//        }
//        assert fragment != null;
//        return fragment;

        Fragment fragment = HomeFragment.newInstance(position + 1);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
