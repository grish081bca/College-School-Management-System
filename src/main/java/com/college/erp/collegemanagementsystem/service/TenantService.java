package com.college.erp.collegemanagementsystem.service;

import java.util.List;
import java.time.LocalDate;

import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;

/**
 * @author grish
 *
 */
public interface TenantService {

    TenantDTO createTenant(TenantCreateRequest request);

    TenantDTO updateTenant(Long id, TenantUpdateRequest request, String remarks);

    default TenantDTO updateTenant(Long id, TenantUpdateRequest request) {
        return updateTenant(id, request, null);
    }

    TenantDTO getTenantById(Long id);

    TenantDTO getTenantByCode(String tenantCode);

    List<TenantDTO> getAllTenants();

    List<TenantDTO> getHeadTenants();

    List<String> getTenantNames();

    List<String> getTenantBranchNames();

    PagablePage<TenantDTO> getTenantsPage(String search, TenantStatus status, Integer page, Integer size);

    PagablePage<TenantDTO> getTenantsPage(String search,
                                          String tenantName,
                                          String tenantCode,
                                          String contactPhone,
                                          String country,
                                          String state,
                                          String city,
                                          LocalDate fromDate,
                                          LocalDate toDate,
                                          TenantStatus status,
                                          Integer page,
                                          Integer size);

    TenantDTO createTenantBranch(TenantCreateRequest request);

    PagablePage<TenantDTO> getTenantBranchesPage(String search,
                                                 Long parentTenantId,
                                                 String tenantName,
                                                 String tenantCode,
                                                 String contactPhone,
                                                 String country,
                                                 String state,
                                                 String city,
                                                 LocalDate fromDate,
                                                 LocalDate toDate,
                                                 TenantStatus status,
                                                 Integer page,
                                                 Integer size);

    TenantDTO activateTenant(Long id);

    TenantDTO deactivateTenant(Long id);

    boolean existsByTenantCode(String tenantCode);

    TenantDTO changeTenantStatus(Long id, TenantStatus status, String remarks);

    default TenantDTO changeTenantStatus(Long id, TenantStatus status) {
        return changeTenantStatus(id, status, null);
    }

    void deleteTenant(Long id);
}
