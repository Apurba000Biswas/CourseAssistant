package com.team73.courseassistant.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.logInFragmentPagerAdapter;
import com.team73.courseassistant.interfaces.MainActivityLuncherListener;

import me.relex.circleindicator.CircleIndicator;

public class LogInActivity2 extends AppCompatActivity implements MainActivityLuncherListener {

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


    @Override
    public void onDoneClicked() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
