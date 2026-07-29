package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserTemplateServiceImpl implements UserTemplateService {

    private final UserTemplateRepository userTemplateRepository;
    private final TenantRepository tenantRepository;

    public UserTemplateServiceImpl(UserTemplateRepository userTemplateRepository, TenantRepository tenantRepository) {
        this.userTemplateRepository = userTemplateRepository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public UserTemplateDTO assignUserTemplate(UserTemplateDTO dto) {
        if (dto == null || dto.getTenantId() == null || dto.getUserType() == null) {
            throw new IllegalArgumentException("Tenant and user type are required.");
        }
        Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(() -> new ResourceNotFoundException("Tenant not found."));
        UserType userType = UserType.valueOf(dto.getUserType());
        UserTemplate template = userTemplateRepository.findByTenant_IdAndUserType(tenant.getId(), userType).orElse(new UserTemplate());
        template.setTenant(tenant);
        template.setUserType(userType);
        template.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? UserStatus.ACTIVE : UserStatus.valueOf(dto.getStatus()));
        return toDto(userTemplateRepository.save(template));
    }

    @Override
    public UserTemplateDTO changeStatus(Long id, UserStatus status) {
        UserTemplate template = userTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User template not found."));
        template.setStatus(status);
        return toDto(userTemplateRepository.save(template));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTemplateDTO> findAll() {
        return userTemplateRepository.findAllByOrderByIdDesc().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTemplateDTO> findByTenant(Long tenantId) {
        return userTemplateRepository.findByTenant_IdAndStatusOrderByUserTypeAsc(tenantId, UserStatus.ACTIVE).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canCreateUserType(Long tenantId, UserType userType) {
        if (userType == UserType.SUPER_ADMIN || userType == UserType.SYSTEM_ADMIN) {
            return true;
        }
        return userTemplateRepository.existsByTenant_IdAndUserTypeAndStatus(tenantId, userType, UserStatus.ACTIVE);
    }

    private UserTemplateDTO toDto(UserTemplate template) {
        UserTemplateDTO dto = new UserTemplateDTO();
        dto.setId(template.getId());
        dto.setTenantId(template.getTenant().getId());
        dto.setTenantName(template.getTenant().getTenantName());
        dto.setUserType(template.getUserType().name());
        dto.setStatus(template.getStatus().name());
        return dto;
    }
}
