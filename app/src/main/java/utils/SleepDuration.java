package utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.time.LocalDateTime;
import java.util.List;

import models.RecyclerViewItem;
import models.SleepDataPopulated;
import models.SleepDateTimeData;

public class SleepDuration extends RecyclerViewItem {

    Calendar calendar = Calendar.getInstance();
    private List<SleepDateTimeData> dateList;
    String sleepTimeAndWakeTime;
    DateTimeFormatter formatter;
    Date currentDateTime;
    Date prevDateTime;
    String durHrMin;

    private String durationAsleepString;

    private static final String TAG = "!!!!DATE!!!->";

    private SleepDataPopulated sleepDataPopulated;
    private List<SleepDataPopulated> allDataList = new ArrayList<>();
    public SleepDuration(List<SleepDateTimeData> dateList) {
        this.dateList = dateList;
    }

    public void getEligibleDates(int i) {
        formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            if (i !=0) {
                currentDateTime=dateList.get(i).getDateDateTime();
                prevDateTime=dateList.get(i-1).getDateDateTime();

                calendar.setTime(prevDateTime);
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDate = calendar.get(Calendar.DATE);

                calendar.setTime(currentDateTime);
                int cYear = calendar.get(Calendar.YEAR);
                int cMonth = calendar.get(Calendar.MONTH);
                int cDate = calendar.get(Calendar.DATE);

                int daysBetween = cDate - pDate;
                if (daysBetween == 1 && pMonth == cMonth && pYear == cYear) {
                    getDuration(i);

                }
            }

    }

    public void getDuration(int i){
        LocalDateTime ldtPrev = LocalDateTime.parse(dateList.get(i-1).getDateTime(),formatter);
        LocalDateTime ldtCurrent= LocalDateTime.parse(dateList.get(i).getDateTime(),formatter);
        Duration duration = Duration.between(ldtPrev,ldtCurrent);
        Long durationLong =duration.getSeconds();
        Long hours = durationLong / 3600;
        Long minutes = (durationLong % 3600) / 60;
        durHrMin = hours+":"+minutes;
        durationAsleepString= hours + " hr " + minutes + " min";
        getSleepTimeAndWakeTime(i);
    }

        public void getSleepTimeAndWakeTime(int i){
            String p=dateList.get(i-1).getDateTime();
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime ldtPrev = LocalDateTime.parse(p,dtf1);
            String prevTime = dtf2.format(ldtPrev);

            String c=dateList.get(i).getDateTime();
            LocalDateTime ldtCurr = LocalDateTime.parse(c,dtf1);
            String currTime = dtf2.format(ldtCurr);

            sleepTimeAndWakeTime = prevTime +" - "+currTime;

            allData();

        }
        public void allData(){
            SimpleDateFormat format = new SimpleDateFormat("dd MMM");
            String dateOfSleep= format.format(prevDateTime);

            sleepDataPopulated = new SleepDataPopulated(durationAsleepString,dateOfSleep,sleepTimeAndWakeTime,durHrMin);

            allDataList.add(sleepDataPopulated);

        }

        public List<SleepDataPopulated> returnedData(){
            return allDataList;
        }


        public List<SleepDateTimeData> getDateList () {
            return dateList;
        }

        public void setDateList (List < SleepDateTimeData > dateList) {
            this.dateList = dateList;
        }


}

