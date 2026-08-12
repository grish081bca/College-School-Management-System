package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.UserDTO;
import com.college.erp.collegemanagementsystem.enums.UserStatus;

import java.util.Optional;

public interface UserService {
    PagablePage<UserDTO> findPage(String q, UserStatus status, Integer page, Integer size);

    UserDTO create(UserDTO userDto, Long tenantId, Long userTemplateId);

    UserDTO update(Long id, UserDTO userDto, Long tenantId, Long userTemplateId);

    Optional<UserDTO> findById(Long id);

    void changeStatus(Long id, UserStatus status);
}
