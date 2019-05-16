package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class SleepDateTimeData {
    private String dateTime;
    private Boolean sensorPressed; //true if sensor is pressed and false if its released (bed pressure sensor)
    SimpleDateFormat formatter;
    String formattedDate;
    Date newDate;
    Date timestamp;

    public SleepDateTimeData(String dateTime,Boolean sensorPressed, Date timestamp){
        this.dateTime=dateTime;
        this.sensorPressed=sensorPressed;
        this.timestamp=timestamp;

    }


    public Date getDateMonthYearHourMinFormat(){
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        formattedDate = formatter.format(timestamp);
        try {
            newDate = formatter.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public Date getDateDateTime(){return timestamp;}

    public void setDateDateTime(Date timestamp){this.timestamp=timestamp;}

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
