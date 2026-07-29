package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.MenuTemplateDTO;
import com.college.erp.collegemanagementsystem.entity.Menu;
import com.college.erp.collegemanagementsystem.entity.MenuTemplate;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.MenuRepository;
import com.college.erp.collegemanagementsystem.repository.MenuTemplateRepository;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.service.MenuTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuTemplateServiceImpl implements MenuTemplateService {

    private final MenuTemplateRepository menuTemplateRepository;
    private final MenuRepository menuRepository;
    private final TenantRepository tenantRepository;

    public MenuTemplateServiceImpl(MenuTemplateRepository menuTemplateRepository,
                                   MenuRepository menuRepository,
                                   TenantRepository tenantRepository) {
        this.menuTemplateRepository = menuTemplateRepository;
        this.menuRepository = menuRepository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public MenuTemplateDTO assignMenuTemplate(MenuTemplateDTO dto) {
        if (dto == null || dto.getUserType() == null || dto.getMenuId() == null) {
            throw new IllegalArgumentException("User type and menu are required.");
        }
        UserType userType = UserType.valueOf(dto.getUserType());
        Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(() -> new ResourceNotFoundException("Menu not found."));
        Tenant tenant = dto.getTenantId() == null ? null : tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found."));

        MenuTemplate template = tenant == null
                ? menuTemplateRepository.findByTenantIsNullAndUserTypeAndMenu_Id(userType, menu.getId()).orElse(new MenuTemplate())
                : menuTemplateRepository.findByTenant_IdAndUserTypeAndMenu_Id(tenant.getId(), userType, menu.getId()).orElse(new MenuTemplate());
        template.setTenant(tenant);
        template.setUserType(userType);
        template.setMenu(menu);
        template.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? MenuStatus.ACTIVE : MenuStatus.valueOf(dto.getStatus()));
        return toDto(menuTemplateRepository.save(template));
    }

    @Override
    public MenuTemplateDTO changeStatus(Long id, MenuStatus status) {
        MenuTemplate template = menuTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu template not found."));
        template.setStatus(status);
        return toDto(menuTemplateRepository.save(template));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuTemplateDTO> findAll() {
        return menuTemplateRepository.findAllByOrderByIdDesc().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findMenusByTenantAndUserType(Long tenantId, UserType userType) {
        if (userType == null) {
            return List.of();
        }
        List<MenuTemplate> templates = userType == UserType.SUPER_ADMIN
                ? menuTemplateRepository.findGlobalActiveTemplates(userType, MenuStatus.ACTIVE)
                : menuTemplateRepository.findActiveTemplates(tenantId, userType, MenuStatus.ACTIVE);
        Map<Long, MenuDTO> menus = new LinkedHashMap<>();
        for (MenuTemplate template : templates) {
            Menu menu = template.getMenu();
            menus.putIfAbsent(menu.getId(), toMenuDto(menu));
        }
        return List.copyOf(menus.values());
    }

    private MenuTemplateDTO toDto(MenuTemplate template) {
        MenuTemplateDTO dto = new MenuTemplateDTO();
        dto.setId(template.getId());
        dto.setTenantId(template.getTenant() != null ? template.getTenant().getId() : null);
        dto.setTenantName(template.getTenant() != null ? template.getTenant().getTenantName() : "Global");
        dto.setUserType(template.getUserType().name());
        dto.setMenuId(template.getMenu().getId());
        dto.setMenuName(template.getMenu().getMenuName());
        dto.setStatus(template.getStatus().name());
        return dto;
    }

    private MenuDTO toMenuDto(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setMenuCode(menu.getMenuCode());
        dto.setMenuName(menu.getMenuName());
        dto.setMenuUrl(menu.getMenuUrl());
        dto.setIcon(menu.getIcon());
        dto.setParentMenuId(menu.getParentMenu() != null ? menu.getParentMenu().getId() : null);
        dto.setParentMenuName(menu.getParentMenu() != null ? menu.getParentMenu().getMenuName() : null);
        dto.setDisplayOrder(menu.getDisplayOrder());
        dto.setStatus(menu.getStatus().name());
        dto.setMenuType(menu.getMenuType().name());
        return dto;
    }
}
