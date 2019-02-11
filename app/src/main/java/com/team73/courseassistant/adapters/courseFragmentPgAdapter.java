package com.team73.courseassistant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.team73.courseassistant.fragments.AverageFragment;
import com.team73.courseassistant.fragments.HomeFragment;
import com.team73.courseassistant.fragments.ProfileFragment;

public class courseFragmentPgAdapter extends FragmentPagerAdapter {

    public courseFragmentPgAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomeFragment();
            case 1:
                return new AverageFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Average";
            default:
                return "Profile";
        }
    }
}
