package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebTenantController {
    private final TenantService service;
    private final CountryService countries;
    private final StateService states;
    private final CityService cities;

    public WebTenantController(TenantService service, CountryService countries, StateService states, CityService cities) {
        this.service = service;
        this.countries = countries;
        this.states = states;
        this.cities = cities;
    }

    @GetMapping("/web/tenants")
    public String list(Model m) {
        add(m);
        return "tenants";
    }

    private void add(Model m) {
        m.addAttribute("tenants", service.getAllTenants());
        m.addAttribute("countries", countries.getAllCountries());
        m.addAttribute("states", states.getAllStates());
        m.addAttribute("cities", cities.getAllCities());
        m.addAttribute("statuses", TenantStatus.values());
        m.addAttribute("tenantRequest", new TenantCreateRequest());
    }

    @PostMapping("/web/tenants")
    public String create(@Valid @ModelAttribute("tenantRequest") TenantCreateRequest r, BindingResult b, Model m, RedirectAttributes a) {
        if (b.hasErrors()) {
            add(m);
            return "tenants";
        }
        service.createTenant(r);
        a.addFlashAttribute("success", "Tenant created successfully.");
        return "redirect:/web/tenants";
    }

    @PostMapping("/web/tenants/{id}/status")
    public String status(@PathVariable Long id, @RequestParam TenantStatus status, RedirectAttributes a) {
        service.changeTenantStatus(id, status);
        a.addFlashAttribute("success", "Tenant status updated successfully.");
        return "redirect:/web/tenants";
    }
}
