package com.example.licensing_service.repository;

import com.example.licensing_service.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

     List<License> findByOrganizationId(String organizationId);

     License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
