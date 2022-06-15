package com.example.myapplication.data.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class VentilatorSession {
    private String nationalID;
    private int heartRate;
    private float oxygenPercentage;
    private float temperature;
    private Map<String,String> symptoms;
    @ServerTimestamp
    private Date date;
    private String illness;
    private String organizationID;
    private String doctorName;

    public VentilatorSession() {
    }

    public VentilatorSession(String nationalID, int heartRate, float oxygenPercentage, float temperature) {
        this.nationalID = nationalID;
        this.heartRate = heartRate;
        this.oxygenPercentage = oxygenPercentage;
        this.temperature = temperature;
    }

    public String getNationalID() {
        return nationalID;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public float getOxygenPercentage() {
        return oxygenPercentage;
    }

    public float getTemperature() {
        return temperature;
    }

    public Date getDate() {
        return date;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public Map<String, String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Map<String, String> symptoms) {
        this.symptoms = symptoms;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
