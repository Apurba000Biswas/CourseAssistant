package com.team73.courseassistant.DataModel;

public class UserProfile {


    public String email;
    public String university;
    public String department;
    public int total_course;
    public int total_semester;
    public int total_credit;

    public UserProfile(){}

    public UserProfile(String university
            , String department
            , int totalCourse
            , int totalSemester
            , int totalCredit){
        this.university = university;
        this.department = department;
        this.total_course = totalCourse;
        this.total_semester = totalSemester;
        this.total_credit = totalCredit;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
