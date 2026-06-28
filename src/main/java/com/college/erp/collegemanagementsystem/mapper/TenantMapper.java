package com.college.erp.collegemanagementsystem.mapper;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.dto.response.TenantResponse;

/**
 * @author grish
 */

public interface TenantMapper {

    Tenant toEntity(TenantCreateRequest request);
    TenantDTO toDto(Tenant entity);
    void updateEntity(TenantUpdateRequest request, Tenant entity);
    List<TenantDTO> toTenantList(List<Tenant> entities);
}
