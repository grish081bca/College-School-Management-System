package com.college.erp.collegemanagementsystem.service.impl;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.dto.response.TenantResponse;
import com.college.erp.collegemanagementsystem.mapper.TenantMapper;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.service.TenantService;
import com.college.erp.collegemanagementsystem.validation.TenantValidationService;
import com.college.erp.collegemanagementsystem.util.TenantCodeGenerator;

/**
 * @author grish
 */

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final TenantValidationService tenantValidationService;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper, TenantValidationService tenantValidationService) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.tenantValidationService = tenantValidationService;
    }

    @Override
    public TenantDTO createTenant(TenantCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request could not be empty.");
        }
        try {
            tenantValidationService.validateCreate(request);
            Tenant tenant = tenantMapper.toEntity(request);
            tenant.setTenantCode(TenantCodeGenerator.generateUniqueCode(tenantRepository::existsByTenantCodeIgnoreCase));
            Tenant savedTenant = tenantRepository.save(tenant);
            return tenantMapper.toDto(savedTenant);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TenantDTO updateTenant(Long id, TenantUpdateRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Tenant is required.");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request could not be empty.");
        }
        try {
            tenantValidationService.validateUpdate(id, request);
            Tenant tenant = getTenantEntityById(id);
            if (tenant == null) {
                throw new ResourceNotFoundException("Tenant not found for update.");
            }
            tenantMapper.updateEntity(request, tenant);
            Tenant savedTenant = tenantRepository.save(tenant);
            return tenantMapper.toDto(savedTenant);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDTO getTenantById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required.");
        }
        Tenant tenant = tenantRepository.findTenantById(id);
        if (tenant == null) {
            throw new ResourceNotFoundException("Tenant not found.");
        }
        return tenantMapper.toDto(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDTO getTenantByCode(String tenantCode) {
        Tenant tenant = tenantRepository.findByTenantCodeIgnoreCase(tenantCode).orElseThrow(() -> new ResourceNotFoundException("Tenant not found with code: " + tenantCode));
        return tenantMapper.toDto(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDTO> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (tenants.isEmpty()) {
            throw new ResourceNotFoundException("No tenants found.");
        }
        return tenantMapper.toTenantList(tenants);
    }

    @Override
    public TenantDTO activateTenant(Long id) {
        Tenant tenant = getTenantEntityById(id);
        if (tenant == null){
            throw new ResourceNotFoundException("Tenant not found.");
        }
        tenant.setStatus(TenantStatus.ACTIVE);
        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantDTO deactivateTenant(Long id) {
        Tenant tenant = getTenantEntityById(id);
        tenant.setStatus(TenantStatus.INACTIVE);
        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    private Tenant getTenantEntityById(Long id) {
        if (id == null) {
            return null;
        }
        Tenant tenant = tenantRepository.findTenantById(id);
        if (tenant != null) {
            return tenant;
        }else {
            return null;
        }
    }

    @Override
    public boolean existsByTenantCode(String tenantCode) {
        if (tenantCode == null) {
            return false;
        }
        return tenantRepository.existsByTenantCodeIgnoreCase(tenantCode);
    }

    @Override
    public TenantDTO changeTenantStatus(Long id, TenantStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Tenant is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required.");
        }
        try {
            Tenant tenant = tenantRepository.findTenantById(id);
            tenant.setStatus(status);
            tenantRepository.save(tenant);
            return tenantMapper.toDto(tenant);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
