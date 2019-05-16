package models;

public class PatientInfo {

    String patientName;
    String patientId;
    String tokenId;

    public PatientInfo(String patientName, String patientId, String tokenId) {
        this.patientName = patientName;
        this.patientId = patientId;
        this.tokenId = tokenId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
