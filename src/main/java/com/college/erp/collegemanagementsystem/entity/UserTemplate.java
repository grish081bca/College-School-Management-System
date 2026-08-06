package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_templates",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_templates_user_type", columnNames = "user_type"),
        indexes = @Index(name = "idx_user_templates_status", columnList = "status"))
public class UserTemplate extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_template_id")
    private MenuTemplate menuTemplate;

    @Column(name = "user_template_name", length = 150)
    private String userTemplateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    // Compatibility accessor for mobile-banking style name (no DB change)
    public String getUserTemplateName() {
        // fallback to userType name if an explicit template name is not present in this schema
        return userType != null ? userType.name() : null;
    }

    public void setUserTemplateName(String name) {
        // no-op: schema does not have a dedicated user_template_name column; keep as no-op to preserve DB
    }
}
