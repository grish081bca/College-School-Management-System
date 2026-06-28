package com.college.erp.collegemanagementsystem.dto.response;

import java.time.OffsetDateTime;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Setter
@Getter
public class TenantResponse {
    private Long id;
    private String tenantCode;
    private String tenantName;
    private String contactEmail;
    private String contactPhone;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private Long countryId;
    private String countryName;
    private Long stateId;
    private String stateName;
    private Long cityId;
    private String cityName;
    private TenantStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
