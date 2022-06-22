package com.example.myapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Disease {
    private String name;
    @SerializedName("Heart rate")
    private Map<String,Integer> heartRate;
    @SerializedName("Oxygen")
    private Map<String,Float> oxygen;
    @SerializedName("Temperature")
    private Map<String,Float> temperature;

    public Disease() {
    }

    public Disease(String name, Map<String, Integer> heartRate, Map<String, Float> oxygen, Map<String, Float> temperature) {
        this.name = name;
        this.heartRate = heartRate;
        this.oxygen = oxygen;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getHeartRate() {
        return heartRate;
    }

    public Map<String, Float> getOxygen() {
        return oxygen;
    }

    public Map<String, Float> getTemperature() {
        return temperature;
    }
}
