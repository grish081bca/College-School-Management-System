package com.college.erp.collegemanagementsystem.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author grish
 *
 */
@Component
public class SecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("system");
            }
            String username = authentication.getName();
            if (username == null || username.trim().isEmpty()) {
                return Optional.of("system");
            }
            return Optional.of(username);
        } catch (Exception ex) {
            return Optional.of("system");
        }
    }
}
