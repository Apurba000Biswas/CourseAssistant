package com.team73.courseassistant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team73.courseassistant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AverageFragment extends Fragment {


    public AverageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_average, container, false);
    }

}
