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
import com.team73.courseassistant.DataModel.Department;
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

    private static final int UNI_SPINNER_KEY = 0;
    private static final int DEPT_SPINNER_KEY = 1;

    private DatabaseReference fbDatabase;

    public LogInAddProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in_add_profile
                , container, false);
        fbDatabase = ((LogInActivity) Objects.requireNonNull(getContext())).getfbDatabaseRef();
        Spinner universitySpinner = rootView.findViewById(R.id.spinner_university);
        Spinner departmentSpinner = rootView.findViewById(R.id.spinner_department);
        setUpUniversitySpinner(universitySpinner);
        setUpDepartmentSpinner(departmentSpinner);
        setDoneClicked(rootView);
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

    private void setUpUniversitySpinner(final Spinner universitySpinner){
        final List<String> universities = new ArrayList<>();
        final DatabaseReference universityTable = fbDatabase.child("courseassistant/university");
        universityTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                for (DataSnapshot ds : data.getChildren()){
                    University uni =  ds.getValue(University.class);
                    assert uni != null;
                    universities.add(uni.name);
                    Log.v("setUpUniversitySpinner", "University = " + uni.name);
                }
                updateSpinner(universities, universitySpinner, UNI_SPINNER_KEY);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateSpinner(List<String> list, Spinner spinner, final int key){
        ArrayAdapter<String> uniSpinnerAdapter =
                new ArrayAdapter<>(Objects.requireNonNull(getContext())
                        , R.layout.drop_down_item
                        , list);
        uniSpinnerAdapter.setDropDownViewResource(
                R.layout.drop_down_item_1line);

        spinner.setAdapter(uniSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (key == UNI_SPINNER_KEY){
                    //its University spinner
                }else if (key == DEPT_SPINNER_KEY){
                    // its Department spinner
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void setUpDepartmentSpinner(final Spinner departmentSpinner){
        final List<String> departments = new ArrayList<>();
        final DatabaseReference deptTable = fbDatabase.child("courseassistant/department");
        deptTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                for (DataSnapshot ds : data.getChildren()){
                    Department dept =  ds.getValue(Department.class);
                    assert dept != null;
                    departments.add(dept.name);
                    Log.v("setUpUniversitySpinner", "Department = " + dept.name);
                }
                updateSpinner(departments, departmentSpinner, DEPT_SPINNER_KEY);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
