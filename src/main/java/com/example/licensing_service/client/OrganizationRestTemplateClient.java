package com.example.licensing_service.client;

import com.example.licensing_service.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganizationDetails(final String organizationId) {
        ResponseEntity<Organization> response =
                restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId);
        return response.getBody();
    }
}
