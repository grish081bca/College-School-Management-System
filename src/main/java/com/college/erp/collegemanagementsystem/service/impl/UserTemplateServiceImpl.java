package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.entity.MenuTemplate;
import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.MenuTemplateRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
@Transactional
public class UserTemplateServiceImpl implements UserTemplateService {

    private final UserTemplateRepository userTemplateRepository;
    private final MenuTemplateRepository menuTemplateRepository;

    public UserTemplateServiceImpl(UserTemplateRepository userTemplateRepository,
                                   MenuTemplateRepository menuTemplateRepository) {
        this.userTemplateRepository = userTemplateRepository;
        this.menuTemplateRepository = menuTemplateRepository;
    }

    @Override
    public UserTemplateDTO assignUserTemplate(UserTemplateDTO dto) {
        if (dto == null || dto.getUserType() == null) {
            throw new IllegalArgumentException("User type is required.");
        }
        UserType userType = UserType.valueOf(dto.getUserType());
        UserTemplate template = userTemplateRepository.findByUserType(userType).orElse(new UserTemplate());
        MenuTemplate menuTemplate = dto.getMenuTemplateId() == null ? null : menuTemplateRepository.findById(dto.getMenuTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu template not found."));
        if (menuTemplate != null && menuTemplate.getUserType() != userType) {
            throw new IllegalArgumentException("Menu template user type must match user template user type.");
        }
        template.setUserType(userType);
        template.setMenuTemplate(menuTemplate);
        template.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? UserStatus.ACTIVE : UserStatus.valueOf(dto.getStatus()));
        return toDto(userTemplateRepository.save(template));
    }

    @Override
    public UserTemplateDTO changeStatus(Long id, UserStatus status) {
        UserTemplate template = userTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User template not found."));
        template.setStatus(status);
        return toDto(userTemplateRepository.save(template));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTemplateDTO> findAll() {
        return userTemplateRepository.findAllByOrderByIdDesc().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PagablePage<UserTemplateDTO> findPage(String search, UserType userType, UserStatus status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(PagablePage.normalizePage(page) - 1, PagablePage.normalizeSize(size), Sort.by(Sort.Direction.DESC, "id"));
        Specification<UserTemplate> specification = (root, query, builder) -> {
            var predicate = builder.conjunction();
            if (search != null && !search.isBlank()) {
                String term = "%" + search.trim().toLowerCase() + "%";
                predicate = builder.and(predicate, builder.like(builder.lower(root.join("menuTemplate", jakarta.persistence.criteria.JoinType.LEFT).get("name")), term));
            }
            if (userType != null) {
                predicate = builder.and(predicate, builder.equal(root.get("userType"), userType));
            }
            if (status != null) {
                predicate = builder.and(predicate, builder.equal(root.get("status"), status));
            }
            return predicate;
        };
        return PagablePage.from(userTemplateRepository.findAll(specification, pageRequest).map(this::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTemplateDTO> findActive() {
        return userTemplateRepository.findByStatusOrderByUserTypeAsc(UserStatus.ACTIVE).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canCreateUserType(UserType userType) {
        if (userType == UserType.SUPER_ADMIN || userType == UserType.SYSTEM_ADMIN) {
            return true;
        }
        return userTemplateRepository.existsByUserTypeAndStatus(userType, UserStatus.ACTIVE);
    }

    private UserTemplateDTO toDto(UserTemplate template) {
        UserTemplateDTO dto = new UserTemplateDTO();
        dto.setId(template.getId());
        dto.setUserType(template.getUserType().name());
        dto.setMenuTemplateId(template.getMenuTemplate() != null ? template.getMenuTemplate().getId() : null);
        dto.setMenuTemplateName(template.getMenuTemplate() != null ? template.getMenuTemplate().getName() : null);
        dto.setStatus(template.getStatus().name());
        return dto;
    }
}
