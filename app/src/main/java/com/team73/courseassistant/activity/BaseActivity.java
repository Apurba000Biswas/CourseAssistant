package com.team73.courseassistant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team73.courseassistant.firebase.FirebaseAuthenticaton;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth = FirebaseAuthenticaton.getAuth(this);
    protected DatabaseReference fbDatabase = FirebaseDatabase.getInstance().getReference();
}
