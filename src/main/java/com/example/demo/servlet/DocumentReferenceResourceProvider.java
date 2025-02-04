package com.example.demo.servlet;

import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.OperationOutcome.IssueType;
import org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.service.ProprietaryApiService;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.validation.FhirValidator;

@Component
public class DocumentReferenceResourceProvider implements IResourceProvider {

    private ProprietaryApiService proprietaryApiService;
    private FhirValidator validator;
    private static final Logger logger = LoggerFactory.getLogger(DocumentReferenceResourceProvider.class);

    public DocumentReferenceResourceProvider(ProprietaryApiService proprietaryApiService, FhirValidator validator) {
        this.proprietaryApiService = proprietaryApiService;
        this.validator = validator;
    }

    @Override
    public Class<DocumentReference> getResourceType() {
        return DocumentReference.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam DocumentReference documentReference) {
        try {

            logger.debug("Received request to create document: " + documentReference);

            // implement validation

            this.proprietaryApiService.sendDocumentData(documentReference);
            logger.debug("document data sent successfully.");
            final var outcome = new MethodOutcome();
            outcome.setResponseStatusCode(201);
            return outcome;

        } catch (Exception e) {
            logger.error("Exception occurred while creating document", e);

            final var operationOutcome = new OperationOutcome();
            OperationOutcomeIssueComponent issue = new OperationOutcomeIssueComponent();
            issue.setSeverity(IssueSeverity.ERROR);
            issue.setCode(IssueType.EXCEPTION);
            issue.setDiagnostics("Internal Server Error");
            operationOutcome.addIssue(issue);
            throw new InternalErrorException("Exception occurred while creating patient", operationOutcome);
        }

    }
}