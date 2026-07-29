package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menus",
        uniqueConstraints = @UniqueConstraint(name = "uk_menus_menu_code", columnNames = "menu_code"),
        indexes = {
                @Index(name = "idx_menus_parent_id", columnList = "parent_menu_id"),
                @Index(name = "idx_menus_status", columnList = "status")
        })
public class Menu extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_code", nullable = false, length = 100)
    private String menuCode;

    @Column(name = "menu_name", nullable = false, length = 150)
    private String menuName;

    @Column(name = "menu_url", length = 255)
    private String menuUrl;

    @Column(name = "icon", length = 100)
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    private Menu parentMenu;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", nullable = false, length = 30)
    private MenuType menuType = MenuType.TENANT;
}
