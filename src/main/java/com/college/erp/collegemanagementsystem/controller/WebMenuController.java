package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;
import com.college.erp.collegemanagementsystem.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebMenuController {

    private final MenuService menuService;

    public WebMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/web/menus")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) MenuStatus status,
                       @RequestParam(required = false) MenuType menuType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model model) {
        model.addAttribute("page", menuService.findPage(q, status, menuType, page, size));
        model.addAttribute("statuses", MenuStatus.values());
        model.addAttribute("menuTypes", MenuType.values());
        model.addAttribute("q", q);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedMenuType", menuType);
        var filters = WebPagination.filters();
        filters.put("q", q);
        filters.put("status", status);
        filters.put("menuType", menuType);
        WebPagination.add(model, "/web/menus", size, filters);
        return "menus";
    }

    @GetMapping("/web/menus/add")
    public String addForm(Model model) {
        add(model);
        return "menu-add";
    }

    @PostMapping("/web/menus")
    public String save(@ModelAttribute MenuDTO dto, RedirectAttributes attributes) {
        menuService.save(dto);
        attributes.addFlashAttribute("success", "Menu saved successfully.");
        return "redirect:/web/menus";
    }

    @PostMapping("/web/menus/{id}/status")
    public String status(@PathVariable Long id, @RequestParam MenuStatus status, RedirectAttributes attributes) {
        menuService.changeStatus(id, status);
        attributes.addFlashAttribute("success", "Menu status updated successfully.");
        return "redirect:/web/menus";
    }

    private void add(Model model) {
        model.addAttribute("menus", menuService.findAll());
        model.addAttribute("activeMenus", menuService.findActive());
        model.addAttribute("statuses", MenuStatus.values());
        model.addAttribute("menuTypes", MenuType.values());
        model.addAttribute("menuRequest", new MenuDTO());
    }
}
