package com.example.demo.service;

import java.text.SimpleDateFormat;

import org.hl7.fhir.r4.model.DocumentReference;

public class FhirDocumentReferenceToProprietaryDocument {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public ProprietarySystemDocument formatDocumentData(DocumentReference documentReference) {

        final var proprietaryDocument = new ProprietarySystemDocument();

        proprietaryDocument.setDateCreated(dateFormat.format(documentReference.getDate()));
        proprietaryDocument
                .setContentB64(
                        documentReference.getContent().get(0).getAttachment().getDataElement().asStringValue());
        proprietaryDocument.setKdlCode(documentReference.getType().getCoding().get(0).getCode());

        // TODO: Read Patient Identifier
        // TODO: Read Encounter ID
        // TODO: Create Handling if ID is not a number etc.

        return proprietaryDocument;

    }

}
