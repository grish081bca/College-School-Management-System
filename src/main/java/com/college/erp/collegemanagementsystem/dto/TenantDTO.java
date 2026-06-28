package com.college.erp.collegemanagementsystem.dto;

import lombok.*;

/**
 * @author grish
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TenantDTO {
    private Long id;
    private String createdDate;
    private String updatedDate;
    private String tenantCode;
    private String tenantName;
    private String contactEmail;
    private String contactEmailSecondary;
    private String contactPhone;
    private String contactPhoneSecondary;
    private String addressLine1;
    private String addressLine2;
    private String countryName;
    private String stateName;
    private String cityName;
    private String status;
    private String postalCode;
}
