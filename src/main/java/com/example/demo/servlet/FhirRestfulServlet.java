package com.example.demo.servlet;

import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/fhir/*", displayName = "FHIR Facade")
public class FhirRestfulServlet extends RestfulServer {

    private List<IResourceProvider> resourceProviders;
    private FhirContext fhirContext;

    public FhirRestfulServlet(List<IResourceProvider> resourceProviders, FhirContext fhirContext) {
        this.resourceProviders = resourceProviders;
        this.fhirContext = fhirContext;
    }

    @Override
    protected void initialize() {
        setFhirContext(fhirContext);
        setResourceProviders(resourceProviders);
    }
}