package com.team73.courseassistant.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.team73.courseassistant.R;
import com.team73.courseassistant.utils.Network;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInEmailFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private static final int REQ_CODE_GOOGLE_SIGN_IN = 32767 / 2;

    private GoogleApiClient google;

    public LogInEmailFragment() {
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_log_in_email
                , container, false);
        setGoogle();
        setSignInClicked(rootView);
        return rootView;
    }

    private void setSignInClicked(View rootView){
        LinearLayout signIn = rootView.findViewById(R.id.ll_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Network.isConnectedToInternet(getContext())){
                    // connect to Google server to log in
                    if (google != null){
                        Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
                        startActivityForResult(intent, REQ_CODE_GOOGLE_SIGN_IN);
                    }
                }else{
                    // no internet connection
                }
            }
        });
    }


    private void setGoogle(){
        // request the user's ID, email address, and basic profile
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // build API client with access to Sign-In API and options above
        google = new GoogleApiClient.Builder(Objects.requireNonNull(getContext()))
                .enableAutoManage(Objects.requireNonNull(getActivity()), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addConnectionCallbacks(this)
                .build();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_CODE_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();

                Toast.makeText(getContext(), "Signed as : " + acct.getEmail(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "Failed ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
