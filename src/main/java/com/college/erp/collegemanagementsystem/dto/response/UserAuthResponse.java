package com.college.erp.collegemanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author grish
 *
 */
@Getter
@Builder
public class UserAuthResponse {

    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String userType;
}
