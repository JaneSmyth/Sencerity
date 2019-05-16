package models;

public class SleepDataPopulated {
    String durationString;
    String dateOfSleep;
    String sleepTimeToWakeTime;
    String dur;

    public SleepDataPopulated(String durationString, String dateOfSleep, String sleepTimeToWakeTime,String dur) {
        this.durationString = durationString;
        this.dateOfSleep = dateOfSleep;
        this.sleepTimeToWakeTime = sleepTimeToWakeTime;
        this.dur=dur;
    }


    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.durationString = dur;
    }
    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getDateOfSleep() {
        return dateOfSleep;
    }

    public void setDateOfSleep(String dateOfSleep) {
        this.dateOfSleep = dateOfSleep;
    }

    public String getSleepTimeToWakeTime() {
        return sleepTimeToWakeTime;
    }

    public void setSleepTimeToWakeTime(String sleepTimeToWakeTime) {
        this.sleepTimeToWakeTime = sleepTimeToWakeTime;
    }
}
