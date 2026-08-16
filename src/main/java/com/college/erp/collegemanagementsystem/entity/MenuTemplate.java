package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author grish
 *
 */
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
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UserType userType;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_template_menus",
            joinColumns = @JoinColumn(name = "menu_template_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @OrderBy("displayOrder ASC, name ASC")
    private List<Menu> menus = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;

    // Backward compatibility: legacy accessors mapping to new field 'name'
    public String getTemplateName() {
        return this.name;
    }

    public void setTemplateName(String templateName) {
        this.name = templateName;
    }

    // mobile-banking style accessor names
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Menu> getMenu() {
        return this.menus;
    }

    public void setMenu(List<Menu> menu) {
        this.menus = menu;
    }
}
