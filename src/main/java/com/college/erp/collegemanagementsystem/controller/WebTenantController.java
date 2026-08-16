package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.dto.TenantDTO;
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
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) TenantStatus status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model m) {
        m.addAttribute("page", service.getTenantsPage(q, status, page, size));
        m.addAttribute("statuses", TenantStatus.values());
        m.addAttribute("q", q);
        m.addAttribute("selectedStatus", status);
        var filters = WebPagination.filters();
        filters.put("q", q);
        filters.put("status", status);
        WebPagination.add(m, "/web/tenants", size, filters);
        return "tenants";
    }

    private void add(Model m) {
        m.addAttribute("tenants", service.getAllTenants());
        m.addAttribute("countries", countries.getAllCountries());
        m.addAttribute("states", states.getAllStates());
        m.addAttribute("cities", cities.getAllCities());
        m.addAttribute("statuses", TenantStatus.values());
        if (!m.containsAttribute("tenantRequest")) {
            m.addAttribute("tenantRequest", new TenantCreateRequest());
        }
        m.addAttribute("isEdit", false);
    }

    @GetMapping("/web/tenants/add")
    public String addForm(Model m) {
        add(m);
        return "tenant-add";
    }

    @PostMapping("/web/tenants")
    public String create(@Valid @ModelAttribute("tenantRequest") TenantCreateRequest r, BindingResult b, Model m, RedirectAttributes a) {
        if (b.hasErrors()) {
            add(m);
            return "tenant-add";
        }
        service.createTenant(r);
        a.addFlashAttribute("success", "Tenant created successfully.");
        return "redirect:/web/tenants";
    }

    @GetMapping("/web/tenants/{id}/edit")
    public String editForm(@PathVariable Long id, Model m, RedirectAttributes a) {
        try {
            TenantDTO tenant = service.getTenantById(id);
            TenantUpdateRequest request = toUpdateRequest(tenant);
            add(m);
            m.addAttribute("tenantRequest", request);
            m.addAttribute("tenantId", id);
            m.addAttribute("isEdit", true);
            return "tenant-add";
        } catch (Exception e) {
            a.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/tenants";
        }
    }

    @PostMapping("/web/tenants/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("tenantRequest") TenantUpdateRequest r, RedirectAttributes a) {
        try {
            service.updateTenant(id, r);
            a.addFlashAttribute("success", "Tenant updated successfully.");
        } catch (Exception e) {
            a.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tenants";
    }

    @GetMapping("/web/tenants/{id}")
    public String view(@PathVariable Long id, Model m, RedirectAttributes a) {
        try {
            m.addAttribute("tenant", service.getTenantById(id));
            return "tenant-detail";
        } catch (Exception e) {
            a.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/tenants";
        }
    }

    @PostMapping("/web/tenants/{id}/status")
    public String status(@PathVariable Long id, @RequestParam TenantStatus status, RedirectAttributes a) {
        service.changeTenantStatus(id, status);
        a.addFlashAttribute("success", "Tenant status updated successfully.");
        return "redirect:/web/tenants";
    }

    private TenantUpdateRequest toUpdateRequest(TenantDTO tenant) {
        TenantUpdateRequest request = new TenantUpdateRequest();
        request.setTenantName(tenant.getTenantName());
        request.setContactEmail(tenant.getContactEmail());
        request.setContactEmailSecondary(tenant.getContactEmailSecondary());
        request.setContactPhone(tenant.getContactPhone());
        request.setContactPhoneSecondary(tenant.getContactPhoneSecondary());
        request.setAddressLine1(tenant.getAddressLine1());
        request.setAddressLine2(tenant.getAddressLine2());
        request.setCountryId(tenant.getCountryId());
        request.setStateId(tenant.getStateId());
        request.setCityId(tenant.getCityId());
        request.setPostalCode(tenant.getPostalCode());
        return request;
    }
}
