package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTemplateDTO {
    private Long id;
    private String userType;
    private Long menuTemplateId;
    private String menuTemplateName;
    private String status;
}
