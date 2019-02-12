package com.team73.courseassistant.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.logInFragmentPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class LogInActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);

        ViewPager viewPager = findViewById(R.id.log_tabs);
        logInFragmentPagerAdapter adapter =
                new logInFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }
}
