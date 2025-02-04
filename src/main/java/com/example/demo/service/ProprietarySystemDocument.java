package com.example.demo.service;

public class ProprietarySystemDocument {
    private String kdlCode;
    private long patientId;
    private long visitNumber;
    private String dateCreated;
    private String contentB64;

    public String getKdlCode() {
        return kdlCode;
    }

    public void setKdlCode(String kdlCode) {
        this.kdlCode = kdlCode;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(long visitNumber) {
        this.visitNumber = visitNumber;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContentB64() {
        return contentB64;
    }

    public void setContentB64(String contentB64) {
        this.contentB64 = contentB64;
    }

}
