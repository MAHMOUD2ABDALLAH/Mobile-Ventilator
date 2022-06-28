package com.example.myapplication.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VentilatorSession implements Parcelable {
    private String nationalID;
    private int heartRate;
    private float oxygenPercentage;
    private float temperature;
    private int ventilatorOxi;
    private HashMap<String,String> symptoms= new HashMap<>();
    @ServerTimestamp
    private Date date;
    private String illness;
    private String organizationID;
    private String doctorName;

    public VentilatorSession() {
    }

    public VentilatorSession(String nationalID, int heartRate, float oxygenPercentage, float temperature, String organizationID, String doctorName) {
        this.nationalID = nationalID;
        this.heartRate = heartRate;
        this.oxygenPercentage = oxygenPercentage;
        this.temperature = temperature;
        this.organizationID = organizationID;
        this.doctorName = doctorName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public float getOxygenPercentage() {
        return oxygenPercentage;
    }

    public void setOxygenPercentage(float oxygenPercentage) {
        this.oxygenPercentage = oxygenPercentage;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getVentilatorOxi() {
        return ventilatorOxi;
    }

    public void setVentilatorOxi(int ventilatorOxi) {
        this.ventilatorOxi = ventilatorOxi;
    }

    public HashMap<String, String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(HashMap<String, String> symptoms) {
        this.symptoms = symptoms;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nationalID);
        dest.writeInt(this.heartRate);
        dest.writeFloat(this.oxygenPercentage);
        dest.writeFloat(this.temperature);
        dest.writeInt(this.ventilatorOxi);
        dest.writeSerializable(this.symptoms);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.illness);
        dest.writeString(this.organizationID);
        dest.writeString(this.doctorName);
    }

    public void readFromParcel(Parcel source) {
        this.nationalID = source.readString();
        this.heartRate = source.readInt();
        this.oxygenPercentage = source.readFloat();
        this.temperature = source.readFloat();
        this.ventilatorOxi = source.readInt();
        this.symptoms = (HashMap<String, String>) source.readSerializable();
        long tmpDate = source.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.illness = source.readString();
        this.organizationID = source.readString();
        this.doctorName = source.readString();
    }

    protected VentilatorSession(Parcel in) {
        this.nationalID = in.readString();
        this.heartRate = in.readInt();
        this.oxygenPercentage = in.readFloat();
        this.temperature = in.readFloat();
        this.ventilatorOxi = in.readInt();
        this.symptoms = (HashMap<String, String>) in.readSerializable();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.illness = in.readString();
        this.organizationID = in.readString();
        this.doctorName = in.readString();
    }

    public static final Parcelable.Creator<VentilatorSession> CREATOR = new Parcelable.Creator<VentilatorSession>() {
        @Override
        public VentilatorSession createFromParcel(Parcel source) {
            return new VentilatorSession(source);
        }

        @Override
        public VentilatorSession[] newArray(int size) {
            return new VentilatorSession[size];
        }
    };
}
