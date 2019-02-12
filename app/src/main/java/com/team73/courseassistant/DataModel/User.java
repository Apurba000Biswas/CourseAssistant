package com.team73.courseassistant.DataModel;

public class User {

    public String email;
    public int id;
    public String name;

    public User(){}

    public String toString(){
        return "User { id= " + id + " name= " + name + " email= " + email;
    }
}
