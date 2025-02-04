package com.example.demo.service;

import java.util.logging.Logger;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProprietaryApiService {

    private static final Logger logger = Logger.getLogger(ProprietaryApiService.class.getName());

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${legacySystemOneURL}")
    public String legacySystemOneURL;

    public void sendPatientData(Patient patient) {

        final var fhirPatientToLegacyOne = new FhirPatientToProprietaryPatient();
        final var legacyPatient = fhirPatientToLegacyOne.formatPatientData(patient);

        logger.info("Sending request to proprietary API: " + legacySystemOneURL);
        logger.info("Request body: " + legacyPatient);

        final var jsonValue = objectMapper.valueToTree(legacyPatient);
        final var response = restTemplate.postForEntity(this.legacySystemOneURL, jsonValue, String.class);

        logger.info("Response from proprietary API: " + response.getStatusCode());

    }
}