package com.team73.courseassistant.firebase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticaton {

    private static final String USER_NAME = "apurba10024@gmail.com";
    private static final String PASSWORD = "caFireAB";

    public static FirebaseAuth getAuth(Context context){
        FirebaseApp.initializeApp(context);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(USER_NAME, PASSWORD);
        return auth;
    }
}
