package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuTemplateDTO {
    private Long id;
    private Long tenantId;
    private String tenantName;
    private String userType;
    private Long menuId;
    private String menuName;
    private String status;
}
