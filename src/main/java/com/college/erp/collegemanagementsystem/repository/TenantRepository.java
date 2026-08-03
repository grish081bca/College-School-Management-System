package com.college.erp.collegemanagementsystem.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.college.erp.collegemanagementsystem.entity.Tenant;

/**
 * @author grish
 *
 */

public interface TenantRepository extends JpaRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {

    Optional<Tenant> findByTenantCodeIgnoreCase(String tenantCode);

    boolean existsByTenantCodeIgnoreCase(String tenantCode);

    boolean existsByTenantCodeIgnoreCaseAndIdNot(String tenantCode, Long id);

    boolean existsByTenantNameIgnoreCase(String tenantName);

    boolean existsByTenantNameIgnoreCaseAndIdNot(String tenantName, Long id);

    Tenant findTenantById(Long id);

    boolean existsByContactEmailAndIdNot(String contactEmail, Long id);

    boolean existsByContactPhoneAndIdNot(String contactPhone, Long id);

    boolean existsByContactEmailSecondaryAndIdNot(String contactEmailSecondary, Long id);

    boolean existsByContactPhoneSecondaryAndIdNot(String contactPhoneSecondary, Long id);

    boolean existsByCountry_Id(Long countryId);

    boolean existsByState_Id(Long stateId);

    boolean existsByCity_Id(Long cityId);

    List<Tenant> findByParentTenant_Id(Long parentTenantId);

    boolean existsByParentTenant_Id(Long parentTenantId);
}
