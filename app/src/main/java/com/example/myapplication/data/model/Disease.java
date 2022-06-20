package com.example.myapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Disease {
    private String name;
    @SerializedName("Heart rate")
    private Map<String,Object> heartRate;
    @SerializedName("Oxygen")
    private Map<String,Object> oxygen;
    @SerializedName("Temperature")
    private Map<String,Object> temperature;

    public Disease() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getHeartRate() {
        return heartRate;
    }

    public Map<String, Object> getOxygen() {
        return oxygen;
    }

    public Map<String, Object> getTemperature() {
        return temperature;
    }
}
