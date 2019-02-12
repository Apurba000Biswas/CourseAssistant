package com.team73.courseassistant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.team73.courseassistant.fragments.LogInAddProfileFragment;
import com.team73.courseassistant.fragments.LogInEmailFragment;

public class logInFragmentPagerAdapter extends FragmentPagerAdapter {
    public logInFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new LogInEmailFragment();
        }else{
            return new LogInAddProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
