package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.request.ChangePasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.ForgotPasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.LoginRequest;
import com.college.erp.collegemanagementsystem.dto.request.RegisterRequest;
import com.college.erp.collegemanagementsystem.dto.request.ResetPasswordRequest;
import com.college.erp.collegemanagementsystem.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author grish
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<RestResponseDTO> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(RestResponseDTO.success("User registered successfully", authenticationService.register(request)), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<RestResponseDTO> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(RestResponseDTO.success("Login successful", authenticationService.login(request)));
    }
    @PostMapping("/change-password")
    public ResponseEntity<RestResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authenticationService.changePassword(request);
        return ResponseEntity.ok(RestResponseDTO.success("Password changed successfully"));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<RestResponseDTO> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(RestResponseDTO.success("Password reset token generated", authenticationService.forgotPassword(request)));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<RestResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(RestResponseDTO.success("Password reset successfully"));
    }
}
