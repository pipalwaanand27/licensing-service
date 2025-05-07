package com.example.licensing_service.client;

import com.example.licensing_service.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

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

}
