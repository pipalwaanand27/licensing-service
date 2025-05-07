package com.example.licensing_service.service;

import com.example.licensing_service.client.OrganizationDiscoveryClient;
import com.example.licensing_service.client.OrganizationFeignClient;
import com.example.licensing_service.client.OrganizationRestTemplateClient;
import com.example.licensing_service.config.ServiceConfig;
import com.example.licensing_service.model.License;
import com.example.licensing_service.model.Organization;
import com.example.licensing_service.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private OrganizationDiscoveryClient discoveryClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    public License getLicense(final String organizationId, final String licenseId, final String clientType) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        System.out.println(license);
        Organization org = retrieveOrganizationInfo(organizationId, clientType);

        return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(serviceConfig.getExampleProperty());
    }

    public List<License> getLicensesByOrganization(final String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(final License license) {
        license.withLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void deleteLicense(final String licenseId) {
        licenseRepository.deleteById(licenseId);
    }

    public void updateLicense(final License license) {
        licenseRepository.save(license);
    }

    private Organization retrieveOrganizationInfo(final String organizationId, final String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganizationDetails(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestTemplateClient.getOrganizationDetails(organizationId);
                break;
            case "discovery": ;
                System.out.println("I am using the discovery client");
                organization = discoveryClient.getOrganization(organizationId);
                break;
            default:
//                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }
}
