package com.example.demo.controller;

import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.OperationOutcome.IssueType;
import org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProprietaryApiService;

import ca.uhn.fhir.context.FhirContext;

@RestController
@RequestMapping("/fhir")
public class FhirController {

    private static final Logger logger = LoggerFactory.getLogger(FhirController.class);
    private final FhirContext fhirContext = FhirContext.forR4();
    private final ProprietaryApiService proprietaryApiService;

    public FhirController(ProprietaryApiService proprietaryApiService) {
        this.proprietaryApiService = proprietaryApiService;
    }

    @PostMapping("/Patient")
    public ResponseEntity<String> createPatient(@RequestBody String patientResource) {
        final var parser = fhirContext.newJsonParser();
        try {
            logger.debug("Received request to create patient: " + patientResource);

            final var patient = parser.parseResource(Patient.class, patientResource);
            this.proprietaryApiService.sendPatientData(patient);

            logger.info("Patient data sent successfully.");

            return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully.");

        } catch (Exception e) {
            logger.error("Exception occurred while creating patient", e);

            final var outcome = new OperationOutcome();
            OperationOutcomeIssueComponent issue = new OperationOutcomeIssueComponent();
            issue.setSeverity(IssueSeverity.ERROR);
            issue.setCode(IssueType.EXCEPTION);
            issue.setDiagnostics("Internal Server Error");
            outcome.addIssue(issue);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(parser.encodeResourceToString(outcome));
        }
    }

}