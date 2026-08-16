package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 *
 */
@Getter
@Setter
public class MenuDTO {
    private Long id;
    private String menuCode;
    private String name;
    private String menuUrl;
    private String icon;
    private Long parentMenuId;
    private String parentMenuName;
    private Integer displayOrder;
    private String status;
    private String menuType;
}
