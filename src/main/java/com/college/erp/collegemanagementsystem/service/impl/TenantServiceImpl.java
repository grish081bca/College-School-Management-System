package com.college.erp.collegemanagementsystem.service.impl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.enums.EntityChangeAction;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.mapper.TenantMapper;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.service.EntityChangeLogService;
import com.college.erp.collegemanagementsystem.service.TenantService;
import com.college.erp.collegemanagementsystem.validation.TenantValidationService;
import com.college.erp.collegemanagementsystem.util.TenantCodeGenerator;

/**
 * @author grish
 *
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {
    private static final String TENANT_ENTITY = "Tenant";
    private static final String CREATE_REMARKS = "Tenant created.";
    private static final String UPDATE_REMARKS = "Tenant updated.";
    private static final String STATUS_REMARKS = "Tenant status changed.";

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final TenantValidationService tenantValidationService;
    private final EntityChangeLogService entityChangeLogService;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper, TenantValidationService tenantValidationService, EntityChangeLogService entityChangeLogService) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.tenantValidationService = tenantValidationService;
        this.entityChangeLogService = entityChangeLogService;
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
            logCreatedTenant(savedTenant);
            return tenantMapper.toDto(savedTenant);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TenantDTO updateTenant(Long id, TenantUpdateRequest request, String remarks) {
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
            TenantDTO before = tenantMapper.toDto(tenant);
            tenantMapper.updateEntity(request, tenant);
            Tenant savedTenant = tenantRepository.save(tenant);
            logUpdatedTenant(before, tenantMapper.toDto(savedTenant), EntityChangeAction.UPDATED, effectiveRemarks(remarks, UPDATE_REMARKS));
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
    @Transactional(readOnly = true)
    public List<String> getTenantNames() {
        return tenantRepository.findAll(Sort.by(Sort.Direction.ASC, "tenantName")).stream().map(Tenant::getTenantName).filter(name -> name != null && !name.isBlank()).distinct().toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PagablePage<TenantDTO> getTenantsPage(String search, TenantStatus status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(PagablePage.normalizePage(page) - 1, PagablePage.normalizeSize(size), Sort.by(Sort.Direction.DESC, "id"));
        Specification<Tenant> specification = (root, query, builder) -> {
            var predicate = builder.conjunction();
            if (search != null && !search.isBlank()) {
                String term = "%" + search.trim().toLowerCase() + "%";
                predicate = builder.and(predicate, builder.or(
                        builder.like(builder.lower(root.get("tenantCode")), term),
                        builder.like(builder.lower(root.get("tenantName")), term),
                        builder.like(builder.lower(root.get("contactEmail")), term),
                        builder.like(builder.lower(root.get("contactPhone")), term)
                ));
            }
            if (status != null) {
                predicate = builder.and(predicate, builder.equal(root.get("status"), status));
            }
            return predicate;
        };
        return PagablePage.from(tenantRepository.findAll(specification, pageRequest).map(tenantMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PagablePage<TenantDTO> getTenantsPage(String tenantName,
                                                 String tenantCode,
                                                 String contactPhone,
                                                 LocalDate fromDate,
                                                 LocalDate toDate,
                                                 TenantStatus status,
                                                 Integer page,
                                                 Integer size) {
        PageRequest pageRequest = PageRequest.of(PagablePage.normalizePage(page) - 1, PagablePage.normalizeSize(size), Sort.by(Sort.Direction.DESC, "id"));
        Specification<Tenant> specification = (root, query, builder) -> {
            var predicate = builder.conjunction();
            if (tenantName != null && !tenantName.isBlank()) {
                predicate = builder.and(predicate, builder.equal(builder.lower(root.get("tenantName")), tenantName.trim().toLowerCase()));
            }
            if (tenantCode != null && !tenantCode.isBlank()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("tenantCode")), "%" + tenantCode.trim().toLowerCase() + "%"));
            }
            if (contactPhone != null && !contactPhone.isBlank()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("contactPhone")), "%" + contactPhone.trim().toLowerCase() + "%"));
            }
            if (fromDate != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("createdAt"), toOffsetDateTime(fromDate)));
            }
            if (toDate != null) {
                predicate = builder.and(predicate, builder.lessThan(root.get("createdAt"), toOffsetDateTime(toDate.plusDays(1))));
            }
            if (status != null) {
                predicate = builder.and(predicate, builder.equal(root.get("status"), status));
            }
            return predicate;
        };
        return PagablePage.from(tenantRepository.findAll(specification, pageRequest).map(tenantMapper::toDto));
    }

    @Override
    public TenantDTO activateTenant(Long id) {
        Tenant tenant = getTenantEntityById(id);
        if (tenant == null){
            throw new ResourceNotFoundException("Tenant not found.");
        }
        TenantStatus oldStatus = tenant.getStatus();
        tenant.setStatus(TenantStatus.ACTIVE);
        TenantDTO dto = tenantMapper.toDto(tenantRepository.save(tenant));
        entityChangeLogService.logChange(TENANT_ENTITY, tenant.getId(), EntityChangeAction.STATUS_CHANGED, "status", oldStatus, tenant.getStatus(), "Tenant activated.");
        return dto;
    }

    @Override
    public TenantDTO deactivateTenant(Long id) {
        Tenant tenant = getTenantEntityById(id);
        TenantStatus oldStatus = tenant.getStatus();
        tenant.setStatus(TenantStatus.INACTIVE);
        TenantDTO dto = tenantMapper.toDto(tenantRepository.save(tenant));
        entityChangeLogService.logChange(TENANT_ENTITY, tenant.getId(), EntityChangeAction.STATUS_CHANGED, "status", oldStatus, tenant.getStatus(), "Tenant deactivated.");
        return dto;
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
    public TenantDTO changeTenantStatus(Long id, TenantStatus status, String remarks) {
        if (id == null) {
            throw new IllegalArgumentException("Tenant is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required.");
        }
        try {
            Tenant tenant = tenantRepository.findTenantById(id);
            if (tenant == null) {
                throw new ResourceNotFoundException("Tenant not found.");
            }
            TenantStatus oldStatus = tenant.getStatus();
            tenant.setStatus(status);
            tenantRepository.save(tenant);
            entityChangeLogService.logChange(TENANT_ENTITY, tenant.getId(), EntityChangeAction.STATUS_CHANGED, "status", oldStatus, status, effectiveRemarks(remarks, STATUS_REMARKS));
            return tenantMapper.toDto(tenant);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteTenant(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tenant id is required.");
        }
        Tenant tenant = tenantRepository.findTenantById(id);
        if (tenant == null) {
            throw new ResourceNotFoundException("Tenant not found for deletion.");
        }
        tenantRepository.delete(tenant);
    }

    private void logCreatedTenant(Tenant tenant) {
        TenantDTO saved = tenantMapper.toDto(tenant);
        logField(saved.getId(), EntityChangeAction.CREATED, "tenantCode", null, saved.getTenantCode(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "tenantName", null, saved.getTenantName(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "contactEmail", null, saved.getContactEmail(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "contactEmailSecondary", null, saved.getContactEmailSecondary(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "contactPhone", null, saved.getContactPhone(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "contactPhoneSecondary", null, saved.getContactPhoneSecondary(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "addressLine1", null, saved.getAddressLine1(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "addressLine2", null, saved.getAddressLine2(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "country", null, saved.getCountryName(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "state", null, saved.getStateName(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "city", null, saved.getCityName(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "postalCode", null, saved.getPostalCode(), CREATE_REMARKS);
        logField(saved.getId(), EntityChangeAction.CREATED, "status", null, saved.getStatus(), CREATE_REMARKS);
    }

    private void logUpdatedTenant(TenantDTO before, TenantDTO after, EntityChangeAction action, String remarks) {
        logField(after.getId(), action, "tenantName", before.getTenantName(), after.getTenantName(), remarks);
        logField(after.getId(), action, "contactEmail", before.getContactEmail(), after.getContactEmail(), remarks);
        logField(after.getId(), action, "contactEmailSecondary", before.getContactEmailSecondary(), after.getContactEmailSecondary(), remarks);
        logField(after.getId(), action, "contactPhone", before.getContactPhone(), after.getContactPhone(), remarks);
        logField(after.getId(), action, "contactPhoneSecondary", before.getContactPhoneSecondary(), after.getContactPhoneSecondary(), remarks);
        logField(after.getId(), action, "addressLine1", before.getAddressLine1(), after.getAddressLine1(), remarks);
        logField(after.getId(), action, "addressLine2", before.getAddressLine2(), after.getAddressLine2(), remarks);
        logField(after.getId(), action, "country", before.getCountryName(), after.getCountryName(), remarks);
        logField(after.getId(), action, "state", before.getStateName(), after.getStateName(), remarks);
        logField(after.getId(), action, "city", before.getCityName(), after.getCityName(), remarks);
        logField(after.getId(), action, "postalCode", before.getPostalCode(), after.getPostalCode(), remarks);
    }

    private void logField(Long tenantId, EntityChangeAction action, String fieldName, Object oldValue, Object newValue, String remarks) {
        entityChangeLogService.logChange(TENANT_ENTITY, tenantId, action, fieldName, oldValue, newValue, remarks);
    }

    private String effectiveRemarks(String remarks, String fallback) {
        String normalized = com.college.erp.collegemanagementsystem.util.ConvertUtils.normalizeText(remarks);
        return normalized == null ? fallback : normalized;
    }

    private OffsetDateTime toOffsetDateTime(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
