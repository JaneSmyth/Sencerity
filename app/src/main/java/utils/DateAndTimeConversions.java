package utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateAndTimeConversions {

    private Date dateToFormat;
    private String formattedDate;
    private SimpleDateFormat formatter;

    public DateAndTimeConversions(Date dateToConvert){
        this.dateToFormat=dateToConvert;

    }

    public String getDateMonthYearFormat(){
       formatter = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
       formattedDate = formatter.format(dateToFormat);

       return formattedDate;

    }
    public String getHourMinuteFormat(){
        formatter = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        formattedDate = formatter.format(dateToFormat);

        return formattedDate;
    }

    public String getDayDateMonthFormat(){
        formatter = new SimpleDateFormat("EEEE, LLL dd",Locale.getDefault());
        formattedDate = formatter.format(dateToFormat);

        return formattedDate;

    }
    public void getHourMinuteSecondsFormat(){

    }
    public void getDurationHoursMinsFormat(){

    }
}
