package models;
//recycler view header item

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepHeader extends RecyclerViewItem{
    private String averageSleepPerWeek;
    private String weekDate;//i.e. week of april 20 -april 27

    public String getAverageSleepPerWeek() {
        return averageSleepPerWeek;
    }

    public void setAverageSleepPerWeek(String averageSleepPerWeek) {
        this.averageSleepPerWeek = averageSleepPerWeek;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }
}
