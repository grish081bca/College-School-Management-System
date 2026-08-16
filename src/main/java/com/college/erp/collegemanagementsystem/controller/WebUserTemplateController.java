package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.service.MenuTemplateService;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author grish
 *
 */
@Controller
public class WebUserTemplateController {

    private final UserTemplateService userTemplateService;
    private final MenuTemplateService menuTemplateService;

    public WebUserTemplateController(UserTemplateService userTemplateService,
                                     MenuTemplateService menuTemplateService) {
        this.userTemplateService = userTemplateService;
        this.menuTemplateService = menuTemplateService;
    }
    @GetMapping("/web/user-templates")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) UserType userType,
                       @RequestParam(required = false) UserStatus status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model model) {
        model.addAttribute("page", userTemplateService.findPage(q, userType, status, page, size));
        model.addAttribute("userTypes", UserType.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("q", q);
        model.addAttribute("selectedUserType", userType);
        model.addAttribute("selectedStatus", status);
        var filters = WebPagination.filters();
        filters.put("q", q);
        filters.put("userType", userType);
        filters.put("status", status);
        WebPagination.add(model, "/web/user-templates", size, filters);
        return "user-templates";
    }
    @GetMapping("/web/user-templates/add")
    public String addForm(Model model) {
        add(model);
        return "user-template-add";
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
        model.addAttribute("menuTemplates", menuTemplateService.findAll());
        model.addAttribute("userTypes", UserType.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("templateRequest", new UserTemplateDTO());
    }
}
