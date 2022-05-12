package com.example.myapplication.data.model;

public class Doctor {
    private String fullName;
    private String email;
    private String password;
    private String gender;
    private String organization;
    private String organizationID;
    private String phone;
    private String workType;

    public Doctor(String fullName,String email,String password, String gender,String organization, String organizationID, String phone, String workType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.organization = organization;
        this.organizationID = organizationID;
        this.phone = phone;
        this.workType = workType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getOrganization() {
        return organization;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getPhone() {
        return phone;
    }

    public String getWorkType() {
        return workType;
    }
}
