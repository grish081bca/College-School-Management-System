package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserTemplateRepository extends JpaRepository<UserTemplate, Long>, JpaSpecificationExecutor<UserTemplate> {

    @EntityGraph(attributePaths = "tenant")
    List<UserTemplate> findAllByOrderByIdDesc();

    List<UserTemplate> findByTenant_IdAndStatusOrderByUserTypeAsc(Long tenantId, UserStatus status);

    boolean existsByTenant_IdAndUserTypeAndStatus(Long tenantId, UserType userType, UserStatus status);

    boolean existsByTenant_IdAndUserType(Long tenantId, UserType userType);

    Optional<UserTemplate> findByTenant_IdAndUserType(Long tenantId, UserType userType);
}
