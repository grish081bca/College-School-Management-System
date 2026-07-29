package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;

import java.util.List;

public interface MenuService {
    MenuDTO save(MenuDTO dto);

    MenuDTO update(Long id, MenuDTO dto);

    MenuDTO findOne(Long id);

    List<MenuDTO> findAll();

    List<MenuDTO> findActive();

    MenuDTO changeStatus(Long id, MenuStatus status);
}
