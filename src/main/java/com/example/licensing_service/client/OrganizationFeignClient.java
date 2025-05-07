package com.example.licensing_service.client;

import com.example.licensing_service.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    @GetMapping(value = "/v1/organizations/{organizationId}", consumes = "application/json")
    Organization getOrganizationDetails(@PathVariable("organizationId") final String organizationId);
}
