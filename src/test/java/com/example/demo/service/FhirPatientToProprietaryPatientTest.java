package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FhirPatientToProprietaryPatientTest {

    FhirPatientToProprietaryPatient coverter = new FhirPatientToProprietaryPatient();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Date birthDate;

    @BeforeEach
    public void setUp() {
        var calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        birthDate = calendar.getTime();
        coverter = new FhirPatientToProprietaryPatient();
    }

    @Test
    public void shouldConvertPatientWithSimpleName() {

        Patient patient = new Patient();
        HumanName name = new HumanName();
        name.addGiven("Heinz");
        name.setFamily("Erhardt");

        patient.addName(name);
        patient.setBirthDate(birthDate);

        var result = coverter.formatPatientData(patient);

        assertEquals("Erhardt", result.getPersonLastName());
        assertEquals("Heinz", result.getPersonFirstName());
        assertEquals(dateFormat.format(birthDate), result.getPersonDOB());

    }

    @Test
    public void shouldConvertPatientWithManyNames() {

        Patient patient = new Patient();
        HumanName name = new HumanName();
        name.addGiven("Heinz");
        name.addGiven("Günther");
        name.setFamily("Erhardt");

        patient.addName(name);
        patient.setBirthDate(birthDate);

        var result = coverter.formatPatientData(patient);

        assertEquals("Erhardt", result.getPersonLastName());
        assertEquals("Heinz Günther", result.getPersonFirstName());
        assertEquals(dateFormat.format(birthDate), result.getPersonDOB());
    }

}
