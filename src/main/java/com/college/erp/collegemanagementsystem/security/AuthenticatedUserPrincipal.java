package com.college.erp.collegemanagementsystem.security;

import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.time.OffsetDateTime;

@Getter
public class AuthenticatedUserPrincipal implements UserDetails {

    private final Long userId;
    private final Long tenantId;
    private final Long userTemplateId;
    private final String tenantCode;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final UserType userType;
    private final String fullName;
    private final OffsetDateTime lastLoginAt;
    private final OffsetDateTime passwordChangedAt;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUserPrincipal(Long userId,
                                      Long tenantId,
                                      Long userTemplateId,
                                      String tenantCode,
                                      String username,
                                      String password,
                                      boolean enabled,
                                      boolean accountNonLocked,
                                      boolean accountNonExpired,
                                      boolean credentialsNonExpired,
                                      UserType userType,
                                      String fullName,
                                      OffsetDateTime lastLoginAt,
                                      OffsetDateTime passwordChangedAt,
                                      Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.userTemplateId = userTemplateId;
        this.tenantCode = tenantCode;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.userType = userType;
        this.fullName = fullName;
        this.lastLoginAt = lastLoginAt;
        this.passwordChangedAt = passwordChangedAt;
        this.authorities = authorities;
    }

    public static AuthenticatedUserPrincipal fromUser(User user, Collection<? extends GrantedAuthority> authorities) {
        return new AuthenticatedUserPrincipal(
                user.getId(),
                user.getTenant().getId(),
                user.getUserTemplate() != null ? user.getUserTemplate().getId() : null,
                user.getTenant().getTenantCode(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.getStatus() == UserStatus.ACTIVE,
                !user.isPasswordResetRequired(),
                user.getUserType(),
                buildFullName(user),
                user.getLastLoginAt(),
                user.getPasswordChangedAt(),
                authorities
        );
    }

    private static String buildFullName(User user) {
        StringBuilder builder = new StringBuilder();
        if (user.getFirstName() != null && !user.getFirstName().isBlank()) {
            builder.append(user.getFirstName().trim());
        }
        if (user.getMiddleName() != null && !user.getMiddleName().isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(user.getMiddleName().trim());
        }
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(user.getLastName().trim());
        }
        return builder.length() == 0 ? user.getUsername() : builder.toString();
    }
}
