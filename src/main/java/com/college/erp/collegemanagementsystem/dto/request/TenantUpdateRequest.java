package com.college.erp.collegemanagementsystem.dto.request;

import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Setter
@Getter
public class TenantUpdateRequest {
    private String tenantName;
    private String contactEmail;
    private String contactEmailSecondary;
    private String contactPhone;
    private String contactPhoneSecondary;
    private String addressLine1;
    private String addressLine2;
    private Long countryId;
    private Long stateId;
    private Long cityId;
    private String postalCode;
}
