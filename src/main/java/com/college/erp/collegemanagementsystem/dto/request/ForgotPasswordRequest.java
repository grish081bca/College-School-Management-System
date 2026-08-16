package com.college.erp.collegemanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 *
 */
@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "Tenant code is required")
    @Size(max = 50, message = "Tenant code must not exceed 50 characters")
    private String tenantCode;
    @NotBlank(message = "Username or email is required")
    @Size(max = 200, message = "Username or email must not exceed 200 characters")
    private String usernameOrEmail;
}
