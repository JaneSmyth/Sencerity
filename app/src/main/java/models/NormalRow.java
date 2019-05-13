package models;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.model.value.NullValue;
import com.google.protobuf.Empty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@IgnoreExtraProperties
public class NormalRow extends RecyclerViewItem {
    /*
        private String sensor;
        private String patientId;
        private Date dateTime;
        private String datePattern;
        private DateTimeFormatter dtf;
        private LocalDateTime localDateTime;
        private String formattedLocalDateTime;
        private LocalDateTime now = LocalDateTime.now();
        private Duration duration;
        private Long durationInSecs;
        private String format;
        private int index;
        private List<LocalDateTime> datesList = new ArrayList<>();
     */
    String date;
    String timeAsleep;
    String sleepingHours;


    public NormalRow(String date, String timeAsleep, String sleepingHours) {
        this.date = date;
        this.timeAsleep = timeAsleep;
        this.sleepingHours = sleepingHours;


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeAsleep() {
        return timeAsleep;
    }

    public void setTimeAsleep(String timeAsleep) {
        this.timeAsleep = timeAsleep;
    }

    public String getSleepingHours() {
        return sleepingHours;
    }

    public void setSleepingHours(String sleepingHours) {
        this.sleepingHours = sleepingHours;
    }
}


















/*
    public Date getDateLong() {
        return dateTime;
    }


    public void setDateLong(Date dateTime) {
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

    public void setLocalDateTime(Date dateTime){

        localDateTime = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());
        datesList.add(index,localDateTime);
    }
    public LocalDateTime getLocalDateTime(){
        datePattern = "EEE, dd MMM yy. HH:mm";
        dtf = DateTimeFormatter.ofPattern(datePattern);
        localDateTime = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());
        formattedLocalDateTime=localDateTime.format(dtf);

        //return localDateTime;
    }
    */
/*
    public String calculateDuration() {
        if (datesList.size() < 2) {
            format = "emptyArray";
        } else {
            duration = Duration.between(datesList.get(index - 1), datesList.get(index));
            durationInSecs = duration.getSeconds();
            format = String.format("%dh %dm",
                    SECONDS.toHours(durationInSecs),
                    SECONDS.toMinutes(durationInSecs) -
                            HOURS.toMinutes(SECONDS.toHours(durationInSecs))
            );
        }
            return format;

    }


    public String formattedDuration(){
        LocalTime localTime = LocalTime.from(d)
        LocalTime localTime = durationInSecs;
        String dateInString = "Mon, 05 May 1980";

        String str =String.valueOf(duration.getSeconds());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Hh:mm");
        LocalDateTime result = LocalDateTime.parse(str, formatter);

        LocalDateTime durationLdt = LocalDateTime.ofInstant(date1.toInstant(),ZoneId.systemDefault());
        String durationPattern="HH:mm";
        dtf = DateTimeFormatter.ofPattern(durationPattern);
        String formattedDuration= dtf.format(durationLdt);

        //String string = date1.toString();
        return null;
    }
 }
*/
