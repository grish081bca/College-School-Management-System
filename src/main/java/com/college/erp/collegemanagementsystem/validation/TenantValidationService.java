package com.college.erp.collegemanagementsystem.validation;

import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.util.ConvertUtils;
import org.springframework.stereotype.Component;
import com.college.erp.collegemanagementsystem.exception.DuplicateResourceException;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;

/**
 * @author grish
 */

@Component
public class TenantValidationService {

    private final TenantRepository tenantRepository;
    private final LocationValidationService locationValidationService;

    public TenantValidationService(TenantRepository tenantRepository, LocationValidationService locationValidationService) {
        this.tenantRepository = tenantRepository;
        this.locationValidationService = locationValidationService;
    }

    public void validateCreate(TenantCreateRequest request) {
        String tenantName = ConvertUtils.normalizeText(request.getTenantName());
        if (tenantRepository.existsByTenantNameIgnoreCase(tenantName)) {
            throw new DuplicateResourceException("Tenant name already exists: " + tenantName);
        }
        validateLocation(request.getCountryId(), request.getStateId(), request.getCityId());
    }

    public void validateUpdate(Long id, TenantUpdateRequest request) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant Not found.");
        }
        String tenantName = ConvertUtils.normalizeText(request.getTenantName());
        if (tenantRepository.existsByTenantNameIgnoreCaseAndIdNot(tenantName, id)) {
            throw new DuplicateResourceException("Name already exists: " + tenantName);
        } else if (tenantRepository.existsByContactEmailAndIdNot(request.getContactEmail(), id)) {
            throw new DuplicateResourceException("Email already exists: " + request.getContactEmail());
        } else if (tenantRepository.existsByContactPhoneAndIdNot(request.getContactPhone(), id)) {
            throw new DuplicateResourceException("Phone number already exists: " + request.getContactPhone());
        }else if(tenantRepository.existsByContactEmailSecondaryAndIdNot(request.getContactEmailSecondary(), id)){
            throw new DuplicateResourceException("Email already exists: " + request.getContactEmail());
        } else if (tenantRepository.existsByContactPhoneSecondaryAndIdNot(request.getContactPhoneSecondary(), id)) {
            throw new DuplicateResourceException("Phone number already exists: " + request.getContactPhone());
        }
        if (request.getCountryId()!=null && request.getStateId() != null && request.getCityId() != null) {
            validateLocation(request.getCountryId(), request.getStateId(), request.getCityId());
        }
    }

    private void validateLocation(Long countryId, Long stateId, Long cityId) {
        if (countryId == null) {
            throw new ResourceNotFoundException("Country is required");
        }
        if (stateId == null) {
            throw new ResourceNotFoundException("State is required");
        }
        if (cityId == null) {
            throw new ResourceNotFoundException("City is required");
        }

        locationValidationService.getCountry(countryId);
        locationValidationService.getState(stateId, countryId);
        locationValidationService.getCity(cityId, stateId);
    }
}
