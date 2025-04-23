package com.example.licensing_service.controller;

import com.example.licensing_service.model.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseController {

    @GetMapping("{licenseId}")
    public License getLicense(@PathVariable("organizationId") final String organizationId,
                              @PathVariable("licenseId") final String licenseId) {
        return new License()
                .withLicenseId(licenseId)
                .withProductName("Telco")
                .withOrganizationId(organizationId);
    }
}
