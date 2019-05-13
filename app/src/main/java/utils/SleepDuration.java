package utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import models.NormalRow;
import models.RecyclerViewItem;
import models.SleepDateTimeData;
import models.SleepHeader;

public class SleepDuration extends RecyclerViewItem {

    private List<SleepDateTimeData> dateList;
    private Date date;
    private Boolean sensorPressed;
   // private int i = 0;

    private String strDayCurrent;
    private int intDayCurrent;
    private String strDayPrev;
    private int intDayPrev;

    private String strMonthCurrent;
    private int intMonthCurrent;
    private String strMonthPrev;
    private int intMonthPrev;

    private String strYearCurrent;
    private int intYearCurrent;
    private String strYearPrev;
    private int intYearPrev;

    private Boolean sensorPressedCurrent;
    private Boolean sensorPressedPrev;

    private  String durationAsleepString;
    private String timeWakeUpAt;
    private String timeFellAsleep;
    private static final String TAG="!!!!DATE!!!->";
    public SleepDuration(List<SleepDateTimeData> dateList) {
            this.dateList = dateList;

    }

    public String durationOfTimeSleep(int i) {
        //while (i < dateList.size()) {
            if (i != 0) {
                String val = dateList.get(i).getDateTime();
                String prevVal = dateList.get(i-1).getDateTime();
                SimpleDateFormat sdfOriginal = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss",Locale.getDefault());



    //DAY (e.g. 01 for first day in month)
                SimpleDateFormat formatDay = new SimpleDateFormat("dd",Locale.getDefault());
                Date dateCurrent = null;
                Date datePrev = null;
                try {
                    dateCurrent = sdfOriginal.parse(val);
                    datePrev = sdfOriginal.parse(prevVal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                strDayCurrent = formatDay.format(dateCurrent);
                intDayCurrent = Integer.parseInt(strDayCurrent);


                strDayPrev = formatDay.format(datePrev);
                intDayPrev = Integer.parseInt(strDayPrev);


    //MONTH (e.g. 02 for February)
                SimpleDateFormat formatMonth = new SimpleDateFormat("MM", Locale.getDefault());

                strMonthCurrent = formatMonth.format(dateCurrent);
                intMonthCurrent = Integer.parseInt(strMonthCurrent);


                strMonthPrev = formatMonth.format(datePrev);
                intMonthPrev = Integer.parseInt(strMonthPrev);


                //YEAR(e.g. 1996)
                SimpleDateFormat formatYear = new SimpleDateFormat("yyyy", Locale.getDefault());

                strYearCurrent = formatYear.format(dateCurrent);
                intYearCurrent = Integer.parseInt(strYearCurrent);

                strYearPrev = formatYear.format(datePrev);
                intYearPrev = Integer.parseInt(strYearPrev);


                //SENSOR DATA

                sensorPressedCurrent = dateList.get(i).getSensorPressed();
                sensorPressedPrev = dateList.get(i - 1).getSensorPressed();
                //COMPARING

                if (intYearPrev == intYearCurrent)//same year
                {


                    if (intMonthPrev == intMonthCurrent)//same month
                    {


                        if (intDayCurrent - intDayPrev == 1)//one day apart
                        {


                            if (!sensorPressedCurrent && sensorPressedPrev) //if current is false AND previous one is true
                            {
                                String current = dateList.get(i).getDateTime();
                                String prev = dateList.get(i-1).getDateTime();
                                DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss");
                                LocalDateTime ldtCurrent = LocalDateTime.parse(current, dtFormatter);
                                LocalDateTime ldtPrev = LocalDateTime.parse(prev, dtFormatter);

                                //LocalDateTime ldtCurrent = LocalDateTime.ofInstant(current.toInstant(),ZoneId.systemDefault());
                                //LocalDateTime ldtPrev = LocalDateTime.ofInstant(prev.toInstant(),ZoneId.systemDefault());
                                Duration duration = Duration.between(ldtPrev,ldtCurrent);
                                Long durationLong =duration.getSeconds();
                                Long hours = durationLong / 3600;
                                Long minutes = (durationLong % 3600) / 60;
                                //Long seconds = (durationLong % 3600) % 60;


                                durationAsleepString= hours + " hr " + minutes + " min";


/*
                               String durationInSecs = String.valueOf(duration.getSeconds());
                                String durationPattern = "HH:mm";
                                String inputPattern="ss";
                                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(inputPattern);
                                DateTimeFormatter formatter= DateTimeFormatter.ofPattern(durationPattern);
                                //durationAsleepString= LocalDateTime.parse(durationInSecs, inputFormat).format(formatter);
                                durationAsleepString = durationInSecs.format(formatter);
*/


                            }
                        }
                    }
                }





            }
            else{
                durationAsleepString=null;
            }
       // }

        return durationAsleepString;
    }

    public String getSleepTimeAndWakeTime(int i){
        String currentTime = dateList.get(i).getDateTime();
        String prevTime= dateList.get(i-1).getDateTime();

        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss");
        DateTimeFormatter formatter2= DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime cLdt = LocalDateTime.parse(currentTime, formatter);
        String timeWakeUpAt = cLdt.format(formatter2);

        LocalDateTime pLdt = LocalDateTime.parse(prevTime, formatter);
        String timeFellAsleep = pLdt.format(formatter2);

        String sleepTimeAndWakeTime = timeFellAsleep + " - " + timeWakeUpAt;

        return sleepTimeAndWakeTime;


    }
    public String getDateOfSleepData(int i){
        String currentDate =dateList.get(i).getDateTime();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss");
        DateTimeFormatter formatter2= DateTimeFormatter.ofPattern("dd MMM");

        LocalDateTime currentDateLdt = LocalDateTime.parse(currentDate, formatter);
        String dateOfSleep = currentDateLdt.format(formatter2);

        return dateOfSleep;

    }



    public List<SleepDateTimeData> getDateList() {
        return dateList;
    }

    public void setDateList(List<SleepDateTimeData> dateList) {
        this.dateList = dateList;
    }
}



/*


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
    */

