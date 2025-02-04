package com.example.demo.servlet;

import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.OperationOutcome.IssueType;
import org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.service.ProprietaryApiService;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;

@Component
public class PatientResourceProvider implements IResourceProvider {

    private ProprietaryApiService proprietaryApiService;
    private static final Logger logger = LoggerFactory.getLogger(PatientResourceProvider.class);

    public PatientResourceProvider(ProprietaryApiService proprietaryApiService) {
        this.proprietaryApiService = proprietaryApiService;
    }

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam Patient patient) {
        final var outcome = new MethodOutcome();
        try {
            logger.debug("Received request to create patient: " + patient);
            this.proprietaryApiService.sendPatientData(patient);
            logger.debug("Patient data sent successfully.");
            outcome.setResponseStatusCode(201);

        } catch (Exception e) {
            logger.error("Exception occurred while creating patient", e);

            final var operationOutcome = new OperationOutcome();
            OperationOutcomeIssueComponent issue = new OperationOutcomeIssueComponent();
            issue.setSeverity(IssueSeverity.ERROR);
            issue.setCode(IssueType.EXCEPTION);
            issue.setDiagnostics("Internal Server Error");
            operationOutcome.addIssue(issue);
            throw new InternalErrorException("Exception occurred while creating patient", operationOutcome);
        }
        return outcome;

    }
}