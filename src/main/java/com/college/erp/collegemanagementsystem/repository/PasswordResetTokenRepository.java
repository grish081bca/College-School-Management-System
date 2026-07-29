package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.PasswordResetToken;
import com.college.erp.collegemanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenHashAndRevokedFalse(String tokenHash);

    Optional<PasswordResetToken> findTopByUserAndRevokedFalseAndUsedAtIsNullOrderByIdDesc(User user);
}
