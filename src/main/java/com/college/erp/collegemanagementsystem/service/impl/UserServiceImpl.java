package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.UserDTO;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.repository.UserRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import com.college.erp.collegemanagementsystem.service.UserService;
import com.college.erp.collegemanagementsystem.util.ConvertUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final UserTemplateRepository userTemplateRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           TenantRepository tenantRepository,
                           UserTemplateRepository userTemplateRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.userTemplateRepository = userTemplateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public PagablePage<UserDTO> findPage(String q, UserStatus status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(PagablePage.normalizePage(page) - 1, PagablePage.normalizeSize(size), Sort.by(Sort.Direction.DESC, "id"));
        Page<User> entities = userRepository.findAll(pageRequest);
        Page<UserDTO> dtoPage = entities.map(ConvertUtils::toUserDTO);
        return PagablePage.from(dtoPage);
    }

    @Override
    public UserDTO create(UserDTO userDto, Long tenantId, Long userTemplateId) {
        if (userDto == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserType(userDto.getUserType());
        user.setStatus(userDto.getStatus());
        user.setEnabled(userDto.isEnabled());

        UserType type = user.getUserType();
        // tenant rules: SUPER_ADMIN and SYSTEM_ADMIN may have null tenant, others require tenant
        if (type == UserType.SUPER_ADMIN || type == UserType.SYSTEM_ADMIN) {
            user.setTenant(null);
        } else {
            if (tenantId == null) {
                throw new IllegalArgumentException("Tenant is required for user type: " + type);
            }
            Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
            user.setTenant(tenant);
        }
        if (userTemplateId != null) {
            UserTemplate ut = userTemplateRepository.findById(userTemplateId).orElse(null);
            user.setUserTemplate(ut);
        }
        // password handling
        if (userDto.getPassword() == null || userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode("changeme"));
        } else {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        User saved = userRepository.save(user);
        return ConvertUtils.toUserDTO(saved);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDto, Long tenantId, Long userTemplateId) {
        if (id == null) {
            throw new IllegalArgumentException("User id is required");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserType(userDto.getUserType());
        user.setStatus(userDto.getStatus());
        user.setEnabled(userDto.isEnabled());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        UserType type = user.getUserType();
        if (type == UserType.SUPER_ADMIN || type == UserType.SYSTEM_ADMIN) {
            user.setTenant(null);
        } else {
            if (tenantId == null) {
                throw new IllegalArgumentException("Tenant is required for user type: " + type);
            }
            Tenant t = tenantRepository.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
            user.setTenant(t);
        }
        if (userTemplateId != null) {
            UserTemplate ut = userTemplateRepository.findById(userTemplateId).orElse(null);
            user.setUserTemplate(ut);
        } else {
            user.setUserTemplate(null);
        }
        User saved = userRepository.save(user);
        return ConvertUtils.toUserDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        if (id == null) return Optional.empty();
        return userRepository.findById(id).map(ConvertUtils::toUserDTO);
    }

    @Override
    public void changeStatus(Long id, UserStatus status) {
        if (id == null) throw new IllegalArgumentException("User id is required");
        if (status == null) throw new IllegalArgumentException("Status is required");
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
    }
}
