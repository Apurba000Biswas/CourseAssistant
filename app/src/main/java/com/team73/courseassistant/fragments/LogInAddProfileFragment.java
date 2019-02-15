package com.team73.courseassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.team73.courseassistant.DataModel.Department;
import com.team73.courseassistant.DataModel.University;
import com.team73.courseassistant.DataModel.UserProfile;
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
    private View rootView;

    public LogInAddProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_in_add_profile
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
                UserProfile newProfile = getValidUserProfile();
                if (newProfile != null){
                    MainActivityLauncherListener listener =
                            (MainActivityLauncherListener) getContext();
                    assert listener != null;
                    listener.onDoneClicked(newProfile);
                }
            }
        });
    }

    private UserProfile getValidUserProfile(){
        EditText etUniversity = rootView.findViewById(R.id.et_university);
        EditText etDepartment = rootView.findViewById(R.id.et_department);
        EditText etTotalCourse = rootView.findViewById(R.id.et_total_course);
        EditText etSemester = rootView.findViewById(R.id.et_total_semester);
        EditText etTotalCredit = rootView.findViewById(R.id.et_total_credit);

        try {
            String university = etUniversity.getText().toString();
            checkLessOrMoreChar(university, "University");
            checkValidString(university, "University");

            String department = etDepartment.getText().toString();
            checkLessOrMoreChar(department, "Department");
            checkValidString(department, "Department");

            checkLessOrMoreChar(etTotalCourse.getText().toString(), "Total course");
            int totalCourse = getValidNum(etTotalCourse.getText().toString(), "Total course");
            checkLessOrMoreChar(etSemester.getText().toString(), "Total semester");
            int totalSemester = getValidNum(etSemester.getText().toString(), "Total semester");
            checkLessOrMoreChar(etTotalCredit.getText().toString(), "Total credit");
            int totalCredit = getValidNum(etTotalCredit.getText().toString(), "Total credit");

            return new UserProfile(university
                    , department, totalCourse, totalSemester, totalCredit);
        } catch (IllegalArgumentException e){
            MainActivityLauncherListener listener =
                    (MainActivityLauncherListener) getContext();
            assert listener != null;
            listener.setStateMessage(e.getMessage());
            return null;
        }
    }

    private int getValidNum(String input, String tag){
        try {
            return Integer.valueOf(input);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(tag + " is not valid!");
        }
    }

    private void checkValidString(String input, String tag){
        for (int i=0; i<input.length(); i++){
            char ch = input.charAt(i);
            if (!Character.isLetter(ch)){
                throw new IllegalArgumentException(tag + " name is not valid!");
            }
        }
    }

    private void checkLessOrMoreChar(String input, String tag){
        if (TextUtils.isEmpty(input)){
            throw new IllegalArgumentException(tag + " can not be Empty!");
        }else if (input.length() > 8){
            throw new IllegalArgumentException(tag + " can not be more than 8 characters!");
        }
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
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    if (key == UNI_SPINNER_KEY){
                        EditText etUniversity = rootView.findViewById(R.id.et_university);
                        etUniversity.setText(selection);
                    }else if (key == DEPT_SPINNER_KEY){
                        EditText etDepartment = rootView.findViewById(R.id.et_department);
                        etDepartment.setText(selection);
                    }
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
                }
                updateSpinner(departments, departmentSpinner, DEPT_SPINNER_KEY);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
