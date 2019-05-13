package models;

import java.time.format.DateTimeFormatter;

public class DressedModel {
    private String patientId;
    private Long dateLong;

    public DressedModel(String patientId, Long dateLong) {
        this.patientId = patientId;
        this.dateLong = dateLong;
    }
 //getters and setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
    }

}
