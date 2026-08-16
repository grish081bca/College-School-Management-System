package com.college.erp.collegemanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author grish
 *
 */
@Getter
@Builder
public class PasswordResetResponse {

    private String resetToken;
    private Long expiresInSeconds;
    private String message;
}
