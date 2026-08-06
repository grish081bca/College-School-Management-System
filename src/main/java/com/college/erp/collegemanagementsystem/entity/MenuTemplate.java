package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "menu_templates",
        uniqueConstraints = @UniqueConstraint(name = "uk_menu_templates_user_type", columnNames = "user_type"),
        indexes = {
                @Index(name = "idx_menu_templates_user_type", columnList = "user_type"),
                @Index(name = "idx_menu_templates_status", columnList = "status")
        })
public class MenuTemplate extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_name", nullable = false, length = 150)
    private String templateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UserType userType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_template_menus",
            joinColumns = @JoinColumn(name = "menu_template_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @OrderBy("displayOrder ASC, menuName ASC")
    private List<Menu> menus = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;
}
