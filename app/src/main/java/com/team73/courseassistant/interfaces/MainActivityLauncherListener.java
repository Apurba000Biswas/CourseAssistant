package com.team73.courseassistant.interfaces;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface MainActivityLauncherListener {
    void onDoneClicked();
    void checkAndLunch(GoogleSignInAccount acct);
}
