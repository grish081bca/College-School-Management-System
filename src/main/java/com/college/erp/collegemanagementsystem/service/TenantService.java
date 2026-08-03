package com.college.erp.collegemanagementsystem.service;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;

/**
 * @author grish
 */

public interface TenantService {

    TenantDTO createTenant(TenantCreateRequest request);

    TenantDTO updateTenant(Long id, TenantUpdateRequest request);

    TenantDTO getTenantById(Long id);

    TenantDTO getTenantByCode(String tenantCode);

    List<TenantDTO> getAllTenants();

    PagablePage<TenantDTO> getTenantsPage(String search, TenantStatus status, Integer page, Integer size);

    TenantDTO activateTenant(Long id);

    TenantDTO deactivateTenant(Long id);

    boolean existsByTenantCode(String tenantCode);

    TenantDTO changeTenantStatus(Long id, TenantStatus status);
}
