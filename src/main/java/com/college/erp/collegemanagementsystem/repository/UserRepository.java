package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {"tenant", "userTemplate", "userTemplate.menuTemplate"})
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = {"tenant", "userTemplate", "userTemplate.menuTemplate"})
    Optional<User> findByUsernameIgnoreCase(String username);

    @EntityGraph(attributePaths = {"tenant", "userTemplate", "userTemplate.menuTemplate"})
    Optional<User> findByTenant_TenantCodeIgnoreCaseAndUsernameIgnoreCase(String tenantCode, String username);

    @EntityGraph(attributePaths = {"tenant", "userTemplate", "userTemplate.menuTemplate"})
    Optional<User> findByTenant_IdAndUsernameIgnoreCase(Long tenantId, String username);

    @EntityGraph(attributePaths = {"tenant", "userTemplate", "userTemplate.menuTemplate"})
    Optional<User> findByTenant_TenantCodeIgnoreCaseAndEmailIgnoreCase(String tenantCode, String email);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTenant_IdAndUsernameIgnoreCase(Long tenantId, String username);

    boolean existsByTenant_IdAndEmailIgnoreCase(Long tenantId, String email);

    long countByTenant_IdAndStatus(Long tenantId, UserStatus status);
}
