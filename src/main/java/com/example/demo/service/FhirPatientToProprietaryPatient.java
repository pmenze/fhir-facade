package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FhirPatientToProprietaryPatient {

    private final static Logger logger = LoggerFactory.getLogger(FhirPatientToProprietaryPatient.class);

    public ProprietarySystemPatient formatPatientData(Patient patient) {

        final String firstName = patient.getName().get(0).getGiven().stream()
                .map(namePart -> namePart.getValue())
                .collect(Collectors.joining(" "));
        final String lastName = patient.getName().get(0).getFamily();
        final String birthDate = new SimpleDateFormat("dd.MM.yyyy").format(patient.getBirthDate());

        logger.debug("Parsed patient data: " + firstName + " " + lastName + ", Birthdate: " + birthDate);

        return new ProprietarySystemPatient(lastName, firstName, birthDate);
    }
}
