package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTemplateDTO {
    private Long id;
    private Long tenantId;
    private String tenantName;
    private String userType;
    private String status;
}
