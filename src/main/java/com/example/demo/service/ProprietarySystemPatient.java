package com.example.demo.service;

public class ProprietarySystemPatient {

    private String PersonLastName;
    private String PersonFirstName;
    private String PersonDOB;

    public ProprietarySystemPatient(String name, String firstName, String birthDate) {
        this.PersonLastName = name;
        this.PersonDOB = birthDate;
        this.PersonFirstName = firstName;
    }

    public String getPersonLastName() {
        return this.PersonLastName;
    }

    public String getPersonDOB() {
        return PersonDOB;
    }

    public void setPersonLastName(String name) {
        this.PersonLastName = name;
    }

    public void setPersonDOB(String birthDate) {
        this.PersonDOB = birthDate;
    }

    public String getPersonFirstName() {
        return PersonFirstName;
    }

    public void setPersonFirstName(String firstName) {
        this.PersonFirstName = firstName;
    }

}
