package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.MenuTemplateDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.service.MenuService;
import com.college.erp.collegemanagementsystem.service.MenuTemplateService;
import com.college.erp.collegemanagementsystem.service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebMenuTemplateController {

    private final MenuTemplateService menuTemplateService;
    private final MenuService menuService;
    private final TenantService tenantService;

    public WebMenuTemplateController(MenuTemplateService menuTemplateService, MenuService menuService, TenantService tenantService) {
        this.menuTemplateService = menuTemplateService;
        this.menuService = menuService;
        this.tenantService = tenantService;
    }

    @GetMapping("/web/menu-templates")
    public String list(Model model) {
        add(model);
        return "menu-templates";
    }

    @PostMapping("/web/menu-templates")
    public String save(@ModelAttribute MenuTemplateDTO dto, RedirectAttributes attributes) {
        menuTemplateService.assignMenuTemplate(dto);
        attributes.addFlashAttribute("success", "Menu template saved successfully.");
        return "redirect:/web/menu-templates";
    }

    @PostMapping("/web/menu-templates/{id}/status")
    public String status(@PathVariable Long id, @RequestParam MenuStatus status, RedirectAttributes attributes) {
        menuTemplateService.changeStatus(id, status);
        attributes.addFlashAttribute("success", "Menu template status updated successfully.");
        return "redirect:/web/menu-templates";
    }

    private void add(Model model) {
        model.addAttribute("templates", menuTemplateService.findAll());
        model.addAttribute("menus", menuService.findActive());
        model.addAttribute("tenants", tenantService.getAllTenants());
        model.addAttribute("userTypes", UserType.values());
        model.addAttribute("statuses", MenuStatus.values());
        model.addAttribute("templateRequest", new MenuTemplateDTO());
    }
}
