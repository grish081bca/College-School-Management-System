package com.college.erp.collegemanagementsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.college.erp.collegemanagementsystem.entity.Tenant;

/**
 * @author grish
 *
 */

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByTenantCodeIgnoreCase(String tenantCode);

    boolean existsByTenantCodeIgnoreCase(String tenantCode);

    boolean existsByTenantCodeIgnoreCaseAndIdNot(String tenantCode, Long id);

    boolean existsByTenantNameIgnoreCase(String tenantName);

    boolean existsByTenantNameIgnoreCaseAndIdNot(String tenantName, Long id);
}
