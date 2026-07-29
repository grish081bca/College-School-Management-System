package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.service.TenantService;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebUserTemplateController {

    private final UserTemplateService userTemplateService;
    private final TenantService tenantService;

    public WebUserTemplateController(UserTemplateService userTemplateService, TenantService tenantService) {
        this.userTemplateService = userTemplateService;
        this.tenantService = tenantService;
    }

    @GetMapping("/web/user-templates")
    public String list(Model model) {
        add(model);
        return "user-templates";
    }

    @PostMapping("/web/user-templates")
    public String save(@ModelAttribute UserTemplateDTO dto, RedirectAttributes attributes) {
        userTemplateService.assignUserTemplate(dto);
        attributes.addFlashAttribute("success", "User template saved successfully.");
        return "redirect:/web/user-templates";
    }

    @PostMapping("/web/user-templates/{id}/status")
    public String status(@PathVariable Long id, @RequestParam UserStatus status, RedirectAttributes attributes) {
        userTemplateService.changeStatus(id, status);
        attributes.addFlashAttribute("success", "User template status updated successfully.");
        return "redirect:/web/user-templates";
    }

    private void add(Model model) {
        model.addAttribute("templates", userTemplateService.findAll());
        model.addAttribute("tenants", tenantService.getAllTenants());
        model.addAttribute("userTypes", UserType.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("templateRequest", new UserTemplateDTO());
    }
}
