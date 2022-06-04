package com.example.myapplication.data.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Patient {
    private String fullName;
    private String password;
    private int age;
    private String nationalID;
    private String gender;
    @ServerTimestamp
    private Date date;

    public Patient() {
    }

    public Patient(String fullName, String password, int age, String nationalID, String gender) {
        this.fullName = fullName;
        this.password = password;
        this.age = age;
        this.nationalID = nationalID;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getNationalID() {
        return nationalID;
    }

    public String getGender() {
        return gender;
    }

    public Date getDate() {
        return date;
    }
}
