package com.team73.courseassistant.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.team73.courseassistant.R;
import com.team73.courseassistant.fragments.AverageFragment;
import com.team73.courseassistant.fragments.HomeFragment;
import com.team73.courseassistant.fragments.ProfileFragment;

public class courseFragmentPgAdapter extends FragmentPagerAdapter {

    private Context mContext;
    /*private int[] imageResId = {
            R.drawable.ic_home,
            R.drawable.ic_average,
            R.drawable.ic_profile
    };*/

    public courseFragmentPgAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
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
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        /*
        Drawable image;
        SpannableString sb;
        switch (position){
            case 0:
                image = mContext.getResources().getDrawable(imageResId[position]);
                sb = new SpannableString("  Home");
                break;
            case 1:
                image = mContext.getResources().getDrawable(imageResId[position]);
                sb = new SpannableString("  Average");
                break;
            default:
                image = mContext.getResources().getDrawable(imageResId[2]);
                sb = new SpannableString("  Profile");
                break;
        }
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_PRIORITY);
        return sb;
        */
        return "";
    }


}
