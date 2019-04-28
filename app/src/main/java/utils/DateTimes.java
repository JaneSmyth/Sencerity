package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.time.LocalDateTime;

import models.RecyclerViewItem;

public class DateTimes extends RecyclerViewItem {


    private Long dlong;
    private LocalDateTime localDateTime;
    private String timePattern;
    private Long dateInMilli;
    private Date dateT;


    public DateTimes(Date dateT){
        this.dateT=dateT;
        setLocalDateTime(dateT);

    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(Date dateT) {
        this.dateT = dateT;
        localDateTime = LocalDateTime.ofInstant(dateT.toInstant(), ZoneId.systemDefault());
        //localDateTime=LocalDateTime.ofInstant(Instant.ofEpochMilli(dateInMilli), ZoneId.systemDefault());
    }

    public String formatDateTime(){
        String datePattern = "EEE, dd MMM yy. HH:mm";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        String d=localDateTime.format(dtf);
        return d;
    }
}
