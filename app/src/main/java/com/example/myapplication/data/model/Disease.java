package com.example.myapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Disease {
    private String name;
    @SerializedName("Heart rate")
    private Map<String,Object> heartRate;
    @SerializedName("Oxygen")
    private Map<String,Object> oxygen;
    @SerializedName("Temperature")
    private Map<String,Object> temperature;
    private float totalAverage;

    public Disease() {
    }

    public Disease(String name, Map<String, Object> heartRate, Map<String, Object> oxygen, Map<String, Object> temperature) {
        this.name = name;
        this.heartRate = heartRate;
        this.oxygen = oxygen;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
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

    public float getTotalAverage() {
        if (heartRate.get("average")!=null){
            return (float) ((int)heartRate.get("average")+ (float)oxygen.get("average")+ (float)temperature.get("average"))/3;
        }else return 0;
    }
}
