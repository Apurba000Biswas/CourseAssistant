package com.team73.courseassistant;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.team73.courseassistant.utils.Network;

public class LogInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private static final int REQ_CODE_GOOGLE_SIGN_IN = 32767 / 2;

    private GoogleApiClient google;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        resources = getResources();
        setGoogle();
    }

    private void setGoogle(){
        // request the user's ID, email address, and basic profile
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // build API client with access to Sign-In API and options above
        google = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addConnectionCallbacks(this)
                .build();
    }

    public void signInClicked(View view) {
        TextView stateTextView = findViewById(R.id.tv_state);
        if (Network.isConnectedToInternet(this)){
            // connect to Google server to log in
            stateTextView.setVisibility(View.GONE);
            if (google != null){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
                startActivityForResult(intent, REQ_CODE_GOOGLE_SIGN_IN);
            }
        }else{
            stateTextView.setVisibility(View.VISIBLE);
            stateTextView.setText(resources.getString(R.string.no_internet));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQ_CODE_GOOGLE_SIGN_IN) {
            TextView stateTextView = findViewById(R.id.tv_state);
            stateTextView.setVisibility(View.VISIBLE);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String msg = resources.getString(R.string.sign_in_success)
                        + " " + acct.getEmail();
                stateTextView.setText(msg);
            } else {
                stateTextView.setText(resources.getString(R.string.log_in_failed));
            }
        }
    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
