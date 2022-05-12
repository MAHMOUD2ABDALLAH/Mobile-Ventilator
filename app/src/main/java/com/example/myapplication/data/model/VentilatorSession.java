package com.example.myapplication.data.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class VentilatorSession {
    private String nationalID;
    private int heartRate;
    private float oxygenPercentage;
    private float temperature;
    private String features;
    @ServerTimestamp
    private Date date;
    private String diseasesType;

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

    public String getFeatures() {
        return features;
    }

    public Date getDate() {
        return date;
    }

    public String getDiseasesType() {
        return diseasesType;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setDiseasesType(String diseasesType) {
        this.diseasesType = diseasesType;
    }
}
