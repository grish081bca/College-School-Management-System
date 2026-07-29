package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu_templates",
        uniqueConstraints = @UniqueConstraint(name = "uk_menu_templates_scope", columnNames = {"tenant_id", "user_type", "menu_id"}),
        indexes = {
                @Index(name = "idx_menu_templates_tenant_user_type", columnList = "tenant_id,user_type"),
                @Index(name = "idx_menu_templates_status", columnList = "status")
        })
public class MenuTemplate extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;
}
