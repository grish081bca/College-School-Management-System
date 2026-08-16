package com.college.erp.collegemanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author grish
 *
 */
@Getter
@Builder
public class AuthResponse {

    private UserAuthResponse user;
    private TenantAuthResponse tenant;
}
