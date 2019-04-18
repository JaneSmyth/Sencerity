package com.example.sencerity;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class SleepDataModel {

    private String sensor;
    private String patientId;
    private Date dateTime;


    public SleepDataModel(Date dateTime, String sensor, String patientId) {
        this.dateTime = dateTime;
        this.sensor = sensor;
        this.patientId = patientId;

    }

    public SleepDataModel(){
        //empty constructor
    }
    public Date getDateTime() {
        return dateTime;
    }

    public String getDateTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, YYYY");
        String d = sdf.format(dateTime);
        return d;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


}