package com.example.licensing_service.service;

import com.example.licensing_service.config.ServiceConfig;
import com.example.licensing_service.model.License;
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

    public License getLicense(final String organizationId, final String licenseId) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
    }

    public List<License> getLicensesByOrganization (final String organizationId) {
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

}
