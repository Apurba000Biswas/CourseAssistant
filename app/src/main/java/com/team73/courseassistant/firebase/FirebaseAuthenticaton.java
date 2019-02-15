package com.team73.courseassistant.firebase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticaton {


    public static FirebaseAuth getAuth(Context context){
        FirebaseApp.initializeApp(context);
        return FirebaseAuth.getInstance();
    }
}
