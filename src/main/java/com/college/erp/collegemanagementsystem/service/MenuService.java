package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;

import java.util.List;

public interface MenuService {
    MenuDTO save(MenuDTO dto);

    MenuDTO update(Long id, MenuDTO dto);

    MenuDTO findOne(Long id);

    List<MenuDTO> findAll();

    PagablePage<MenuDTO> findPage(String search, MenuStatus status, MenuType menuType, Integer page, Integer size);

    List<MenuDTO> findActive();

    MenuDTO changeStatus(Long id, MenuStatus status);
}
