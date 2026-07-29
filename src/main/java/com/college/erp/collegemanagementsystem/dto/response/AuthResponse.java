package com.college.erp.collegemanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private UserAuthResponse user;
    private TenantAuthResponse tenant;
}
