package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.MenuTemplateDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;

import java.util.List;

public interface MenuTemplateService {
    MenuTemplateDTO assignMenuTemplate(MenuTemplateDTO dto);

    MenuTemplateDTO changeStatus(Long id, MenuStatus status);

    List<MenuTemplateDTO> findAll();

    List<MenuDTO> findMenusByTenantAndUserType(Long tenantId, UserType userType);
}
