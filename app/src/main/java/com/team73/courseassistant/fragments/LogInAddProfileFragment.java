package com.team73.courseassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.team73.courseassistant.DataModel.University;
import com.team73.courseassistant.R;
import com.team73.courseassistant.activity.LogInActivity;
import com.team73.courseassistant.interfaces.MainActivityLauncherListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInAddProfileFragment extends Fragment{

    private DatabaseReference fbDatabase;
    private Spinner universitySpinner;

    public LogInAddProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in_add_profile
                , container, false);
        fbDatabase = ((LogInActivity) Objects.requireNonNull(getContext())).getfbDatabaseRef();
        universitySpinner = rootView.findViewById(R.id.spinner_university);
        loadUniversity();
        setDoneClicked(rootView);
        //updateUniSpinner(rootView);
        return rootView;
    }

    private void setDoneClicked(View rootView){
        LinearLayout done = rootView.findViewById(R.id.ll_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityLauncherListener listener =
                        (MainActivityLauncherListener) getContext();
                assert listener != null;
                listener.onDoneClicked();
            }
        });
    }

    private void loadUniversity(){
        final DatabaseReference universityTable = fbDatabase.child("courseassistant/university");
        final List<String> universities = new ArrayList<>();
        universityTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                for (DataSnapshot ds : data.getChildren()){
                    University uni =  ds.getValue(University.class);
                    universities.add(uni.name);
                    Log.v("loadUniversity", "University = " + uni.name);
                }
                updateUniSpinner(universities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUniSpinner(List<String> universities){
        ArrayAdapter<String> uniSpinnerAdapter =
                new ArrayAdapter<>(getContext()
                        , R.layout.drop_down_item
                        , universities);
        uniSpinnerAdapter.setDropDownViewResource(
                R.layout.drop_down_item_1line);

        universitySpinner.setAdapter(uniSpinnerAdapter);
        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}
