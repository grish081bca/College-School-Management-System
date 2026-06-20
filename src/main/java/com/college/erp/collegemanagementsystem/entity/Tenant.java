package com.college.erp.collegemanagementsystem.entity;

import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 *
 */

@Entity
@Getter
@Setter
@Table(
        name = "tenants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tenants_tenant_code", columnNames = "tenant_code"),
                @UniqueConstraint(name = "uk_tenants_tenant_name", columnNames = "tenant_name")
        }
)
public class Tenant extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_code", nullable = false, length = 50)
    private String tenantCode;

    @Column(name = "tenant_name", nullable = false, length = 200)
    private String tenantName;

    @Column(name = "contact_email", length = 200, nullable = false)
    private String contactEmail;

    @Column(name = "contact_email_secondary", nullable = true, length = 200)
    private String contactEmailSecondary;

    @Column(name = "contact_phone", length = 15)
    private String contactPhone;

    @Column(name = "contact_phone_secondary", nullable = true, length = 15)
    private String contactPhoneSecondary;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "city_id")
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TenantStatus status;

    @PrePersist
    protected void ensureStatus() {
        if (status == null) {
            status = TenantStatus.ACTIVE;
        }
    }
}
