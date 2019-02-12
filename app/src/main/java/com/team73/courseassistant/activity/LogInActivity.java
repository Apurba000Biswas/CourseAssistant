package com.team73.courseassistant.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.logInFragmentPagerAdapter;
import com.team73.courseassistant.firebase.FirebaseAuthenticaton;
import com.team73.courseassistant.interfaces.MainActivityLauncherListener;

import me.relex.circleindicator.CircleIndicator;

public class LogInActivity extends AppCompatActivity implements
        MainActivityLauncherListener {

    private FirebaseAuth mAuth;
    //protected DatabaseReference fbDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ViewPager viewPager = findViewById(R.id.log_tabs);
        logInFragmentPagerAdapter adapter =
                new logInFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        mAuth = FirebaseAuthenticaton.getAuth(this);
    }


    @Override
    public void onDoneClicked() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        //finish();
    }
}
