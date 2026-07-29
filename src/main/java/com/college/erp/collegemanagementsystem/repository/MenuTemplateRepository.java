package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.MenuTemplate;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuTemplateRepository extends JpaRepository<MenuTemplate, Long> {

    @EntityGraph(attributePaths = {"tenant", "menu", "menu.parentMenu"})
    List<MenuTemplate> findAllByOrderByIdDesc();

    boolean existsByTenant_IdAndUserTypeAndMenu_Id(Long tenantId, UserType userType, Long menuId);

    Optional<MenuTemplate> findByTenant_IdAndUserTypeAndMenu_Id(Long tenantId, UserType userType, Long menuId);

    Optional<MenuTemplate> findByTenantIsNullAndUserTypeAndMenu_Id(UserType userType, Long menuId);

    @Query("""
            select mt from MenuTemplate mt
            join fetch mt.menu m
            left join fetch m.parentMenu
            left join fetch mt.tenant
            where mt.userType = :userType
              and mt.status = :status
              and m.status = :status
              and (mt.tenant.id = :tenantId or mt.tenant is null)
            order by m.displayOrder asc, m.menuName asc
            """)
    List<MenuTemplate> findActiveTemplates(@Param("tenantId") Long tenantId,
                                           @Param("userType") UserType userType,
                                           @Param("status") MenuStatus status);

    @Query("""
            select mt from MenuTemplate mt
            join fetch mt.menu m
            left join fetch m.parentMenu
            left join fetch mt.tenant
            where mt.userType = :userType
              and mt.status = :status
              and m.status = :status
              and mt.tenant is null
            order by m.displayOrder asc, m.menuName asc
            """)
    List<MenuTemplate> findGlobalActiveTemplates(@Param("userType") UserType userType,
                                                 @Param("status") MenuStatus status);
}
