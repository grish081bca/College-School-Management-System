package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.entity.Menu;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;
import com.college.erp.collegemanagementsystem.exception.DuplicateResourceException;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.MenuRepository;
import com.college.erp.collegemanagementsystem.service.MenuService;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuDTO save(MenuDTO dto) {
        validate(dto, null);
        Menu menu = new Menu();
        copy(dto, menu);
        return toDto(menuRepository.save(menu));
    }

    @Override
    public MenuDTO update(Long id, MenuDTO dto) {
        validate(dto, id);
        Menu menu = getMenu(id);
        copy(dto, menu);
        return toDto(menuRepository.save(menu));
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO findOne(Long id) {
        return toDto(getMenu(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findAll() {
        return menuRepository.findAll(Sort.by(Sort.Direction.ASC, "displayOrder", "menuName")).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PagablePage<MenuDTO> findPage(String search, MenuStatus status, MenuType menuType, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(PagablePage.normalizePage(page) - 1, PagablePage.normalizeSize(size), Sort.by(Sort.Direction.ASC, "displayOrder", "menuName"));
        Specification<Menu> specification = (root, query, builder) -> {
            var predicate = builder.conjunction();
            if (search != null && !search.isBlank()) {
                String term = "%" + search.trim().toLowerCase() + "%";
                predicate = builder.and(predicate, builder.or(
                        builder.like(builder.lower(root.get("menuCode")), term),
                        builder.like(builder.lower(root.get("menuName")), term),
                        builder.like(builder.lower(root.get("menuUrl")), term)
                ));
            }
            if (status != null) {
                predicate = builder.and(predicate, builder.equal(root.get("status"), status));
            }
            if (menuType != null) {
                predicate = builder.and(predicate, builder.equal(root.get("menuType"), menuType));
            }
            return predicate;
        };
        return PagablePage.from(menuRepository.findAll(specification, pageRequest).map(this::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findActive() {
        return menuRepository.findByStatusOrderByDisplayOrderAscMenuNameAsc(MenuStatus.ACTIVE).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public MenuDTO changeStatus(Long id, MenuStatus status) {
        Menu menu = getMenu(id);
        menu.setStatus(status);
        return toDto(menuRepository.save(menu));
    }

    private void validate(MenuDTO dto, Long id) {
        if (dto == null || dto.getMenuCode() == null || dto.getMenuCode().isBlank()) {
            throw new IllegalArgumentException("Menu code is required.");
        }
        if (dto.getMenuName() == null || dto.getMenuName().isBlank()) {
            throw new IllegalArgumentException("Menu name is required.");
        }
        boolean duplicate = id == null
                ? menuRepository.existsByMenuCodeIgnoreCase(dto.getMenuCode())
                : menuRepository.existsByMenuCodeIgnoreCaseAndIdNot(dto.getMenuCode(), id);
        if (duplicate) {
            throw new DuplicateResourceException("Menu code already exists.");
        }
    }

    private void copy(MenuDTO dto, Menu menu) {
        menu.setMenuCode(dto.getMenuCode().trim().toUpperCase());
        menu.setMenuName(dto.getMenuName().trim());
        menu.setMenuUrl(dto.getMenuUrl());
        menu.setIcon(dto.getIcon());
        menu.setDisplayOrder(dto.getDisplayOrder() == null ? 0 : dto.getDisplayOrder());
        menu.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? MenuStatus.ACTIVE : MenuStatus.valueOf(dto.getStatus()));
        menu.setMenuType(dto.getMenuType() == null || dto.getMenuType().isBlank() ? MenuType.SUB_MENU : MenuType.valueOf(dto.getMenuType()));
        menu.setParentMenu(dto.getParentMenuId() == null ? null : getMenu(dto.getParentMenuId()));
    }

    private Menu getMenu(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Menu is required.");
        }
        return menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu not found."));
    }

    private MenuDTO toDto(Menu menu) {
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
