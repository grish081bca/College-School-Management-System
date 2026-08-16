package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.UserDTO;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.service.TenantService;
import com.college.erp.collegemanagementsystem.service.UserService;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * @author grish
 *
 */
@Controller
public class WebUserController {
    private final com.college.erp.collegemanagementsystem.service.UserService userService;
    private final TenantService tenantService;
    private final UserTemplateService userTemplateService;

    public WebUserController(UserService userService,
                             TenantService tenantService,
                             UserTemplateService userTemplateService) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.userTemplateService = userTemplateService;
    }
    @GetMapping("/web/users")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) UserStatus status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model m) {
        m.addAttribute("page", userService.findPage(q, status, page, size));
        m.addAttribute("q", q);
        m.addAttribute("selectedStatus", status);
        m.addAttribute("statuses", UserStatus.values());
        m.addAttribute("userTypes", UserType.values());
        var filters = WebPagination.filters();
        filters.put("q", q);
        filters.put("status", status);
        WebPagination.add(m, "/web/users", size, filters);
        return "users";
    }

    private void addModelAttributes(Model m) {
        m.addAttribute("tenants", tenantService.getAllTenants());
        m.addAttribute("userTemplates", userTemplateService.findAll());
        m.addAttribute("userTypes", UserType.values());
        m.addAttribute("statuses", UserStatus.values());
        m.addAttribute("user", new UserDTO());
    }
    @GetMapping("/web/users/add")
    public String addForm(Model m) {
        addModelAttributes(m);
        return "user-add";
    }
    @PostMapping("/web/users")
    public String create(@ModelAttribute UserDTO user,
                         @RequestParam(required = false) Long tenantId,
                         @RequestParam(required = false) Long userTemplateId,
                         RedirectAttributes attributes) {
        try {
            userService.create(user, tenantId, userTemplateId);
            attributes.addFlashAttribute("success", "User saved successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/users/add";
        }
        return "redirect:/web/users";
    }
    @GetMapping("/web/users/{id}/edit")
    public String editForm(@PathVariable Long id, Model m, RedirectAttributes attributes) {
        Optional<UserDTO> u = userService.findById(id);
        if (u.isEmpty()) {
            attributes.addFlashAttribute("error", "User not found.");
            return "redirect:/web/users";
        }
        addModelAttributes(m);
        m.addAttribute("user", u.get());
        return "user-add"; // reuse add form for edit
    }
    @PostMapping("/web/users/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute UserDTO formUser,
                         @RequestParam(required = false) Long tenantId,
                         @RequestParam(required = false) Long userTemplateId,
                         RedirectAttributes attributes) {
        try {
            userService.update(id, formUser, tenantId, userTemplateId);
            attributes.addFlashAttribute("success", "User updated successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/users";
    }
    @GetMapping("/web/users/{id}")
    public String view(@PathVariable Long id, Model m, RedirectAttributes attributes) {
        Optional<UserDTO> u = userService.findById(id);
        if (u.isEmpty()) {
            attributes.addFlashAttribute("error", "User not found.");
            return "redirect:/web/users";
        }
        m.addAttribute("user", u.get());
        return "user-detail";
    }
    @PostMapping("/web/users/{id}/status")
    public String status(@PathVariable Long id, @RequestParam UserStatus status, RedirectAttributes attributes) {
        try {
            userService.changeStatus(id, status);
            attributes.addFlashAttribute("success", "User status updated successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/users";
    }
}
