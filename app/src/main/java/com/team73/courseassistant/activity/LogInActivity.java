package com.team73.courseassistant.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import java.util.Objects;

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
        //finish();
    }


    @Override
    public void checkAndLunch(final GoogleSignInAccount acct) {
        final String signedEmail = acct.getEmail();
        final DatabaseReference userTable = fbDatabase.child("courseassistant/users");
        Query query = userTable.orderByChild("email").equalTo(signedEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                try {
                    User user = data.getChildren().iterator().next().getValue(User.class);
                    if (Objects.requireNonNull(user).email.equals(signedEmail)){
                        Intent mainActivityIntent =
                                new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(mainActivityIntent);
                    }
                } catch (NoSuchElementException e){
                    // not exist
                    TextView tvState = findViewById(R.id.tv_state);
                    tvState.setText(getResources().getString(R.string.no_record));
                    //String signedName = acct.getDisplayName();
                    //String signedPhotoUrl = "" + acct.getPhotoUrl();

                    DatabaseReference newUser = userTable.push();
                    //newUser.child("email").setValue(signedEmail);
                    //newUser.child("name").setValue(signedName);
                    //newUser.child("photo").setValue(signedPhotoUrl);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
