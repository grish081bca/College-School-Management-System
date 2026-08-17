package com.college.erp.collegemanagementsystem.dto;

import lombok.*;

/**
 * @author grish
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TenantDTO {
    private Long id;
    private String createdDate;
    private String updatedDate;
    private String createdBy;
    private String updatedBy;
    private String tenantCode;
    private String tenantName;
    private String tenantType;
    private Long parentTenantId;
    private String parentTenantCode;
    private String parentTenantName;
    private String contactEmail;
    private String contactEmailSecondary;
    private String contactPhone;
    private String contactPhoneSecondary;
    private String addressLine1;
    private String addressLine2;
    private Long countryId;
    private String countryName;
    private Long stateId;
    private String stateName;
    private Long cityId;
    private String cityName;
    private String status;
    private String postalCode;
}
