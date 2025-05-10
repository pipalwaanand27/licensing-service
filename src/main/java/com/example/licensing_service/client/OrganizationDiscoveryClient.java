package com.example.licensing_service.client;

import com.example.licensing_service.model.Organization;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Log4j2
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    @CircuitBreaker(name = "organizationService", fallbackMethod = "getOrganizationServiceFallback")
    @Retry(name = "organizationService")
    public Organization getOrganization(final String organizationId) {
        final RestTemplate restTemplate = new RestTemplate();
        final List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("organizationservice");
        System.out.println(serviceInstanceList);
        if (serviceInstanceList.size() == 0)
            return null;
        final String serviceUrl = String.format("%s/v1/organizations/%s",
                serviceInstanceList.get(0).getUri().toString(), organizationId);
        System.out.println(serviceUrl);
        ResponseEntity<Organization> response =
                restTemplate.exchange(serviceUrl, HttpMethod.GET, null, Organization.class, organizationId);
        return response.getBody();
    }

    public Organization getOrganizationServiceFallback(final String organizationId, final Throwable throwable) {

        log.error("Fallback triggered due to: {}", throwable.getMessage());
        final Organization organization = new Organization();
        organization.setId(organizationId);
        organization.setName("Default Organization");
        organization.setContactEmail("orgdefault@gmail.com");
        organization.setContactName("Default contact name");
        organization.setContactPhone("9876543210");
        return organization;
    }

}
