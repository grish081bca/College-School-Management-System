package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.dto.request.ChangePasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.ForgotPasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.LoginRequest;
import com.college.erp.collegemanagementsystem.dto.request.RegisterRequest;
import com.college.erp.collegemanagementsystem.dto.request.ResetPasswordRequest;
import com.college.erp.collegemanagementsystem.dto.response.AuthResponse;
import com.college.erp.collegemanagementsystem.dto.response.PasswordResetResponse;
import com.college.erp.collegemanagementsystem.dto.response.TenantAuthResponse;
import com.college.erp.collegemanagementsystem.dto.response.UserAuthResponse;
import com.college.erp.collegemanagementsystem.entity.PasswordResetToken;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.exception.DuplicateResourceException;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.repository.PasswordResetTokenRepository;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import com.college.erp.collegemanagementsystem.repository.UserRepository;
import com.college.erp.collegemanagementsystem.security.AuthenticatedUserPrincipal;
import com.college.erp.collegemanagementsystem.service.AuthenticationService;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserTemplateService userTemplateService;
    private final UserTemplateRepository userTemplateRepository;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     TenantRepository tenantRepository,
                                     PasswordResetTokenRepository passwordResetTokenRepository,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager,
                                     UserTemplateService userTemplateService,
                                     UserTemplateRepository userTemplateRepository) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userTemplateService = userTemplateService;
        this.userTemplateRepository = userTemplateRepository;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        validatePasswordConfirmation(request.getPassword(), request.getConfirmPassword());
        Tenant tenant = tenantRepository.findByTenantCodeIgnoreCase(request.getTenantCode())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        if (tenant.getStatus() != TenantStatus.ACTIVE) {
            throw new IllegalStateException("Tenant is not active");
        }
        if (!userTemplateService.canCreateUserType(request.getUserType())) {
            throw new IllegalStateException("User type is not allowed for this tenant");
        }
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setTenant(tenant);
        user.setUsername(request.getUsername().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFirstName(request.getFirstName().trim());
        user.setMiddleName(request.getMiddleName() != null ? request.getMiddleName().trim() : null);
        user.setLastName(request.getLastName().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setPasswordResetRequired(false);
        user.setUserType(request.getUserType());
        userTemplateRepository.findByUserType(request.getUserType())
                .ifPresent(user::setUserTemplate);
        User savedUser = userRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword()));
        AuthenticatedUserPrincipal principal = (AuthenticatedUserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getStatus() != UserStatus.ACTIVE || !user.isEnabled() || !user.isAccountNonLocked()) {
            throw new IllegalStateException("User account is not active");
        }
        user.setLastLoginAt(OffsetDateTime.now());
        userRepository.save(user);
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUserPrincipal principal)) {
            throw new IllegalStateException("Authenticated user is required");
        }
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        validatePasswordConfirmation(request.getNewPassword(), request.getConfirmPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(OffsetDateTime.now());
        user.setPasswordResetRequired(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public PasswordResetResponse forgotPassword(ForgotPasswordRequest request) {
        Tenant tenant = tenantRepository.findByTenantCodeIgnoreCase(request.getTenantCode())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        User user = userRepository.findByTenant_TenantCodeIgnoreCaseAndUsernameIgnoreCase(tenant.getTenantCode(), request.getUsernameOrEmail())
                .or(() -> userRepository.findByTenant_TenantCodeIgnoreCaseAndEmailIgnoreCase(tenant.getTenantCode(), request.getUsernameOrEmail()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String rawResetToken = UUID.randomUUID() + "." + UUID.randomUUID();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setTokenHash(hashToken(rawResetToken));
        resetToken.setExpiresAt(OffsetDateTime.now().plusHours(1));
        passwordResetTokenRepository.save(resetToken);

        user.setPasswordResetRequired(true);
        userRepository.save(user);

        return PasswordResetResponse.builder()
                .resetToken(rawResetToken)
                .expiresInSeconds(3600L)
                .message("Password reset token generated")
                .build();
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        validatePasswordConfirmation(request.getNewPassword(), request.getConfirmPassword());
        String tokenHash = hashToken(request.getResetToken());
        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenHashAndRevokedFalse(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Reset token not found"));
        if (resetToken.getUsedAt() != null || resetToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Reset token expired or already used");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(OffsetDateTime.now());
        user.setPasswordResetRequired(false);
        userRepository.save(user);

        resetToken.setUsedAt(OffsetDateTime.now());
        resetToken.setRevoked(true);
        passwordResetTokenRepository.save(resetToken);
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .user(UserAuthResponse.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(buildFullName(user))
                        .userType(user.getUserType().name())
                        .build())
                .tenant(TenantAuthResponse.builder()
                        .tenantId(user.getTenant().getId())
                        .tenantCode(user.getTenant().getTenantCode())
                        .tenantName(user.getTenant().getTenantName())
                        .tenantType(user.getTenant().getTenantType())
                        .build())
                .build();
    }

    private void validatePasswordConfirmation(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and confirmation password do not match");
        }
    }

    private String buildFullName(User user) {
        StringBuilder builder = new StringBuilder();
        if (user.getFirstName() != null) {
            builder.append(user.getFirstName());
        }
        if (user.getMiddleName() != null && !user.getMiddleName().isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(user.getMiddleName());
        }
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(user.getLastName());
        }
        return builder.toString();
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hashed) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Unable to hash token", exception);
        }
    }
}
