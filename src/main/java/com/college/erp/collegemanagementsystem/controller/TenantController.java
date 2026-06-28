package com.college.erp.collegemanagementsystem.controller;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.enums.ResponseStatus;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.dto.response.TenantResponse;
import com.college.erp.collegemanagementsystem.service.TenantService;

/**
 * @author grish
 */

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestResponseDTO> createTenant(@Valid @RequestBody TenantCreateRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Request could not be empty"));
        }else if (request.getTenantName() == null || request.getTenantName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Tenant Name is required"));
        } else if (request.getContactEmail() == null || request.getContactEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Contact Email is required"));
        } else if (request.getContactPhone() == null || request.getContactPhone().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Contact Phone is required"));
        } else if (request.getStateId() == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("State is required"));
        }else if (request.getCityId() == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("City is required"));
        }else if (request.getCountryId() == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Country is required"));
        }
        try {
            TenantDTO response = tenantService.createTenant(request);
            return new ResponseEntity<>(RestResponseDTO.success("Tenant Created Successfully", response), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(RestResponseDTO.internalServerError(e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponseDTO> updateTenant(@Param("id") Long id, @Valid @RequestBody TenantUpdateRequest request) {
        RestResponseDTO restResponseDTO = new RestResponseDTO();
        if (id == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Tenant must be provided."));
        }
        try {
            TenantDTO response = tenantService.updateTenant(id, request);
            return ResponseEntity.ok(RestResponseDTO.success("Tenant Updated Successfully", response));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(RestResponseDTO.internalServerError(e.getMessage()));
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<RestResponseDTO> getTenantById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Tenant Id is required"));
        }
        try {
            TenantDTO tenantDetail =  tenantService.getTenantById(id);
            return ResponseEntity.ok(RestResponseDTO.success("Tenant Found Successfully", tenantDetail));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(RestResponseDTO.internalServerError(e.getMessage()));
        }
    }

    @GetMapping("/getByTenantCode")
    public ResponseEntity<RestResponseDTO> getTenantByCode(@RequestParam String tenantCode) {
        if (tenantCode == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Tenant Code is required"));
        }
        try {
            TenantDTO tenantDetail =  tenantService.getTenantByCode(tenantCode);
            return ResponseEntity.ok(RestResponseDTO.success("Tenant Found Successfully", tenantDetail));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(RestResponseDTO.internalServerError(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> getAllTenants() {
        try {
            List<TenantDTO> tenantList = tenantService.getAllTenants();
            return ResponseEntity.ok(RestResponseDTO.success("Tenants Found Successfully", tenantList));
        }catch (Exception e){
            return new ResponseEntity<>(RestResponseDTO.failure(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<RestResponseDTO> changeTenantStatus(@Param("id") Long id, @Param("status") TenantStatus status) {
        if (id == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Tenant Id is required"));
        }
        if (status == null) {
            return ResponseEntity.badRequest().body(RestResponseDTO.badRequest("Status is required"));
        }
        try {
            TenantDTO tenant = tenantService.changeTenantStatus(id, status);
            return ResponseEntity.ok(RestResponseDTO.success("Tenant Status Changed Successfully", tenant));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(RestResponseDTO.internalServerError(e.getMessage()));
        }
    }
}
