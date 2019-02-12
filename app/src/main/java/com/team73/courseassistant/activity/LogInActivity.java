package com.team73.courseassistant.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team73.courseassistant.DataModel.User;
import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.logInFragmentPagerAdapter;
import com.team73.courseassistant.interfaces.MainActivityLauncherListener;

import java.util.NoSuchElementException;

import me.relex.circleindicator.CircleIndicator;

public class LogInActivity extends BaseActivity implements
        MainActivityLauncherListener {

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
    }


    @Override
    public void onDoneClicked() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        showData();
        //finish();
    }

    private void showData(){
        DatabaseReference userTable = fbDatabase.child("courseassistant/users");
        Query query = userTable.orderByChild("name").equalTo("Apurba");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                // data is an array of matched query
                User user = data.getChildren().iterator().next().getValue(User.class);
                Log.v("LoginActivity", "" + user.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void checkAndLunch(GoogleSignInAccount acct) {
        final String signedEmail = acct.getEmail();
        DatabaseReference userTable = fbDatabase.child("courseassistant/users");
        Query query = userTable.orderByChild("email").equalTo(signedEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                try {
                    User user = data.getChildren().iterator().next().getValue(User.class);
                    if (user.email.equals(signedEmail)){
                        Intent mainActivityIntent =
                                new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(mainActivityIntent);
                    }
                } catch (NoSuchElementException e){
                    // not exist
                    Toast.makeText(LogInActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
