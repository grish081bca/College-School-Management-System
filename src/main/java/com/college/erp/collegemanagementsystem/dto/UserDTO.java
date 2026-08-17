package com.college.erp.collegemanagementsystem.dto;

import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author grish
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String createdDate;
    private String updatedDate;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private UserStatus status;
    private boolean enabled = true;
    private UserType userType;
    private Long tenantId; // nullable for SUPER_ADMIN / SYSTEM_ADMIN
    private String tenantName;
    private Long userTemplateId;
    private String userTemplateName;
}
