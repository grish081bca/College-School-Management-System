package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.MenuTemplate;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuTemplateRepository extends JpaRepository<MenuTemplate, Long>, JpaSpecificationExecutor<MenuTemplate> {

    @EntityGraph(attributePaths = {"menus", "menus.parentMenu"})
    List<MenuTemplate> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = {"menus", "menus.parentMenu"})
    List<MenuTemplate> findAllByOrderByNameAsc();

    Optional<MenuTemplate> findByUserType(UserType userType);

    @Query("""
            select mt from MenuTemplate mt
            join fetch mt.menus m
            left join fetch m.parentMenu
            where mt.userType = :userType
              and mt.status = :status
              and m.status = :status
            order by m.displayOrder asc, m.name asc
            """)
    List<MenuTemplate> findActiveTemplates(@Param("userType") UserType userType,
                                           @Param("status") MenuStatus status);
}
