package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;

import java.util.List;

public interface UserTemplateService {
    UserTemplateDTO assignUserTemplate(UserTemplateDTO dto);

    UserTemplateDTO changeStatus(Long id, UserStatus status);

    List<UserTemplateDTO> findAll();

    PagablePage<UserTemplateDTO> findPage(String search, Long tenantId, UserType userType, UserStatus status, Integer page, Integer size);

    List<UserTemplateDTO> findByTenant(Long tenantId);

    boolean canCreateUserType(Long tenantId, UserType userType);
}
