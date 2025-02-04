package com.example.demo.servlet;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.validation.FhirValidator;

@Configuration
public class FhirConfig {
    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }

    @Bean
    public RestfulServer restfulServer(List<IResourceProvider> resourceProviders) {
        RestfulServer server = new RestfulServer(fhirContext());
        server.setResourceProviders(resourceProviders);
        return server;
    }

    @Bean
    public FhirValidator fhirValidator(FhirContext fhirContext) {
        FhirValidator validator = fhirContext.newValidator();

        // TODO: Implement validation against the ISiK profile

        return validator;
    }
}