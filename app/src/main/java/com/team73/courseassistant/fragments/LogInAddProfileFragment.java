package com.team73.courseassistant.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.team73.courseassistant.R;
import com.team73.courseassistant.activity.MainActivity;
import com.team73.courseassistant.interfaces.MainActivityLuncherListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInAddProfileFragment extends Fragment {


    public LogInAddProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in_add_profile
                , container, false);
        setDoneClicked(rootView);
        return rootView;
    }

    private void setDoneClicked(View rootView){
        LinearLayout done = rootView.findViewById(R.id.ll_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityLuncherListener listener =
                        (MainActivityLuncherListener) getContext();
                assert listener != null;
                listener.onDoneClicked();
            }
        });
    }

}
