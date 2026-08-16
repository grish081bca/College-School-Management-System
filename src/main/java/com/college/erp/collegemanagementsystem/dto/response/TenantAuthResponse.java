package com.college.erp.collegemanagementsystem.dto.response;

import com.college.erp.collegemanagementsystem.enums.TenantType;
import lombok.Builder;
import lombok.Getter;

/**
 * @author grish
 *
 */
@Getter
@Builder
public class TenantAuthResponse {

    private Long tenantId;
    private String tenantCode;
    private String tenantName;
    private TenantType tenantType;
}
