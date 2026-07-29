package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.security.AuthenticatedUserPrincipal;
import com.college.erp.collegemanagementsystem.service.MenuTemplateService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuModelAdvice {

    private final MenuTemplateService menuTemplateService;

    public MenuModelAdvice(MenuTemplateService menuTemplateService) {
        this.menuTemplateService = menuTemplateService;
    }

    @ModelAttribute
    public void addMenus(Model model, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUserPrincipal principal)) {
            model.addAttribute("allowedMenus", java.util.List.of());
            return;
        }
        model.addAttribute("allowedMenus", menuTemplateService.findMenusByTenantAndUserType(principal.getTenantId(), principal.getUserType()));
        model.addAttribute("loggedInUserType", principal.getUserType());
    }
}
