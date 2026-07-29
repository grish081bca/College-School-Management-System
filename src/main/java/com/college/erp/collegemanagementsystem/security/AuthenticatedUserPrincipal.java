package com.college.erp.collegemanagementsystem.security;

import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.enums.UserRole;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class AuthenticatedUserPrincipal implements UserDetails {

    private final Long userId;
    private final Long tenantId;
    private final String tenantCode;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final UserRole userRole;
    private final UserType userType;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUserPrincipal(Long userId,
                                      Long tenantId,
                                      String tenantCode,
                                      String username,
                                      String password,
                                      boolean enabled,
                                      boolean accountNonLocked,
                                      boolean accountNonExpired,
                                      boolean credentialsNonExpired,
                                      UserRole userRole,
                                      UserType userType,
                                      Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.tenantCode = tenantCode;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.userRole = userRole;
        this.userType = userType;
        this.authorities = authorities;
    }

    public static AuthenticatedUserPrincipal fromUser(User user, Collection<? extends GrantedAuthority> authorities) {
        return new AuthenticatedUserPrincipal(
                user.getId(),
                user.getTenant().getId(),
                user.getTenant().getTenantCode(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.getStatus() == UserStatus.ACTIVE,
                !user.isPasswordResetRequired(),
                user.getUserRole(),
                user.getUserType(),
                authorities
        );
    }
}
