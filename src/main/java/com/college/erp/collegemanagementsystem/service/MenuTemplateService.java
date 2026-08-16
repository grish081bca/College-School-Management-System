package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.MenuTemplateDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;

import java.util.List;

/**
 * @author grish
 *
 */
public interface MenuTemplateService {
    MenuTemplateDTO assignMenuTemplate(MenuTemplateDTO dto);

    MenuTemplateDTO changeStatus(Long id, MenuStatus status);

    List<MenuTemplateDTO> findAll();

    PagablePage<MenuTemplateDTO> findPage(String search, UserType userType, MenuStatus status, Integer page, Integer size);

    List<MenuDTO> findMenusByUserType(UserType userType);

    List<MenuDTO> findMenusForUserTemplate(Long userTemplateId, UserType userType);
}
