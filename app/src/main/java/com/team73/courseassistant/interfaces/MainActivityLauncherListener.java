package com.team73.courseassistant.interfaces;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.team73.courseassistant.DataModel.UserProfile;

public interface MainActivityLauncherListener {
    void onDoneClicked(UserProfile newProfile);
    void checkAndLunch(GoogleSignInAccount acct);
    void setStateMessage(String message);
}
