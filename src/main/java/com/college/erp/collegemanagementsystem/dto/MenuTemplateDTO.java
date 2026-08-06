package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuTemplateDTO {
    private Long id;
    private String name;
    private String userType;
    private Long tenantId;
    private String tenantName;
    private Long menuId;
    private String menuName;
    private List<Long> menuIds = new ArrayList<>();
    private List<String> menuNames = new ArrayList<>();
    private String status;
}
