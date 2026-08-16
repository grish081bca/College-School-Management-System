package com.college.erp.collegemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author grish
 *
 */
@Getter
@Setter
public class MenuTemplateDTO {
    private Long id;
    private String name;
    private String userType;
    private Long menuId;
    private String menuName;
    private List<Long> menuIds = new ArrayList<>();
    private List<String> menuNames = new ArrayList<>();
    private String status;

    // Backward compatibility for form binding from 'templateName' input in JSPs
    public String getTemplateName() { return this.name; }
    public void setTemplateName(String templateName) { this.name = templateName; }
}
