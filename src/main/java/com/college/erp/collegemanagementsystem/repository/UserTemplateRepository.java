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

    @EntityGraph(attributePaths = "menuTemplate")
    List<UserTemplate> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = "menuTemplate")
    List<UserTemplate> findByStatusOrderByUserTypeAsc(UserStatus status);

    boolean existsByUserTypeAndStatus(UserType userType, UserStatus status);

    boolean existsByUserType(UserType userType);

    @EntityGraph(attributePaths = "menuTemplate")
    Optional<UserTemplate> findByUserType(UserType userType);

    @EntityGraph(attributePaths = "menuTemplate")
    List<UserTemplate> findAllByUserTypeOrderByIdAsc(UserType userType);
}
