package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.Menu;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
    Optional<Menu> findByMenuCodeIgnoreCase(String menuCode);

    boolean existsByMenuCodeIgnoreCase(String menuCode);

    boolean existsByMenuCodeIgnoreCaseAndIdNot(String menuCode, Long id);

    List<Menu> findByStatusOrderByDisplayOrderAscMenuNameAsc(MenuStatus status);
}
