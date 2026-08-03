package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.ChangePasswordRequest;
import com.college.erp.collegemanagementsystem.dto.request.ResetPasswordRequest;
import com.college.erp.collegemanagementsystem.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebAuthController {

    private final AuthenticationService authenticationService;

    public WebAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/web/my-profile")
    public String myProfile() {
        return "my-profile";
    }

    @GetMapping("/web/change-password")
    public String changePasswordForm() {
        return "change-password";
    }

    @PostMapping("/web/change-password")
    public String changePassword(@ModelAttribute ChangePasswordRequest request, RedirectAttributes attributes) {
        try {
            authenticationService.changePassword(request);
            attributes.addFlashAttribute("success", "Password changed successfully.");
            return "redirect:/web/my-profile";
        } catch (RuntimeException exception) {
            attributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/web/change-password";
        }
    }

    @GetMapping("/web/reset-password")
    public String resetPasswordForm() {
        return "reset-password";
    }

    @PostMapping("/web/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordRequest request, RedirectAttributes attributes) {
        try {
            authenticationService.resetPassword(request);
            attributes.addFlashAttribute("success", "Password reset successfully.");
            return "redirect:/web/my-profile";
        } catch (RuntimeException exception) {
            attributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/web/reset-password";
        }
    }
}
