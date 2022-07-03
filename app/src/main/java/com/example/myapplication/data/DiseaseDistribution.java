package com.example.myapplication.data;

public class DiseaseDistribution {
    private String diseaseName;
    private float value;

    public DiseaseDistribution(String diseaseName, float value) {
        this.diseaseName = diseaseName;
        this.value = value;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" +
                "diseaseName=" + diseaseName + '\'' +
                ", value=" + value + " ";
    }
}
