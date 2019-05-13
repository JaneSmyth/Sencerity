package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepDateTimeData {
    private String dateTime;
    private Boolean sensorPressed; //true if sensor is pressed and false if its released (bed pressure sensor)

    public SleepDateTimeData(String dateTime,Boolean sensorPressed){
        this.dateTime=dateTime;
        this.sensorPressed=sensorPressed;

    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getSensorPressed() {
        return sensorPressed;
    }

    public void setSensorPressed(Boolean sensorPressed) {
        this.sensorPressed = sensorPressed;
    }

}
