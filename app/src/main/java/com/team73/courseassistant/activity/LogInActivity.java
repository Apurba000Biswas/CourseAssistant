package com.team73.courseassistant.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team73.courseassistant.DataModel.User;
import com.team73.courseassistant.DataModel.UserProfile;
import com.team73.courseassistant.R;
import com.team73.courseassistant.adapters.logInFragmentPagerAdapter;
import com.team73.courseassistant.interfaces.MainActivityLauncherListener;

import java.util.NoSuchElementException;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

public class LogInActivity extends BaseActivity implements
        MainActivityLauncherListener {

    private GoogleSignInAccount acct;

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
    public void onDoneClicked(UserProfile newProfile) {
        if (acct != null){
            String email = acct.getEmail();
            newProfile.setEmail(email);
            // now do the fire base push operation here
            saveTheProfile(newProfile);
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            //finish();
        }else{
            TextView stateTextView = findViewById(R.id.tv_state);
            stateTextView.setText(getResources().getString(R.string.log_in_first));
        }
    }

    private void saveTheProfile(UserProfile profile){
        // push new User
        pushUserAcct();
        // push new University
        pushUniversity(profile.university);
        // push new Department
        //pushDepartment(profile.department);
        // push new profile
        /*
        final DatabaseReference profileTable = fbDatabase.child("courseassistant/profiles");
        DatabaseReference newProf = profileTable.push();
        newProf.child("university").setValue(profile.university);
        newProf.child("department").setValue(profile.department);
        newProf.child("email").setValue(profile.email);
        newProf.child("total_course").setValue(profile.total_course);
        newProf.child("total_credit").setValue(profile.total_credit);
        newProf.child("total_semester").setValue(profile.total_semester);
        */
    }

    private void pushUniversity(final String university){
        final DatabaseReference universityTable = fbDatabase.child("courseassistant/university");
        Query query = universityTable.orderByChild("name").equalTo(university);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    DatabaseReference newUni = universityTable.push();
                    newUni.child("name").setValue(university);
                }
                /*
                try{
                    // yes this university exist
                }catch (NoSuchElementException e){
                    // no such university exist
                    DatabaseReference newUni = universityTable.push();
                    newUni.child("name").setValue(university);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void pushDepartment(final String department){
        final DatabaseReference departmentTable = fbDatabase.child("courseassistant/department");
        Query query = departmentTable.orderByChild("name").equalTo(department);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    DatabaseReference newDept = departmentTable.push();
                    newDept.child("name").setValue(department);
                }
                /*
                try{
                    // yes this department exist
                }catch (NoSuchElementException e){
                    // no such department
                    DatabaseReference newDept = departmentTable.push();
                    newDept.child("name").setValue(department);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushUserAcct(){
        final DatabaseReference userTable = fbDatabase.child("courseassistant/users");
        final String signedEmail = acct.getEmail();
        Query query = userTable.orderByChild("email").equalTo(signedEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    DatabaseReference newUser = userTable.push();
                    newUser.child("email").setValue(signedEmail);
                    newUser.child("name").setValue(acct.getDisplayName());
                    Uri photoUri = acct.getPhotoUrl();
                    String photo = "Not Available";
                    if (photoUri != null){
                        photo = photoUri.toString();
                    }
                    newUser.child("photo").setValue(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void checkAndLunch(final GoogleSignInAccount acct) {
        this.acct = acct;
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
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setStateMessage(String message) {
        TextView stateTextView = findViewById(R.id.tv_state);
        stateTextView.setText(message);
    }

    public DatabaseReference getfbDatabaseRef(){
        return fbDatabase;
    }
}
