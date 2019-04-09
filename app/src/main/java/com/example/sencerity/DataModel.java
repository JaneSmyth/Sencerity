package com.example.sencerity;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.Timestamp;


public class DataModel {

    String dateAndTime;
    String sensor;
    Boolean sensorPressure;

    public DataModel(){
        //required empty
    }

    public DataModel(String dateAndTime, String sensor, Boolean sensorPressure)
    {
        this.dateAndTime=dateAndTime;
        this.sensor=sensor;
        this.sensorPressure=sensorPressure;

    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Boolean getSensorPressure() {
        return sensorPressure;
    }

    public void setSensorPressure(Boolean sensorPressure) {
        this.sensorPressure = sensorPressure;
    }
}
