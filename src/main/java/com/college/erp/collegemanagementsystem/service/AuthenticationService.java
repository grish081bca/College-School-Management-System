package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.dto.request.ChangePasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.ForgotPasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.LoginRequest;
import com.college.erp.collegemanagementsystem.dto.request.RegisterRequest;
import com.college.erp.collegemanagementsystem.dto.request.ResetPasswordRequest;
import com.college.erp.collegemanagementsystem.dto.response.AuthResponse;
import com.college.erp.collegemanagementsystem.dto.response.PasswordResetResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void changePassword(ChangePasswordRequest request);

    PasswordResetResponse forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
