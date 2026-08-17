package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * @author grish
 *
 */
@Controller
public class WebTenantController {
    private static final String TENANT_ENTITY = "Tenant";

    private final TenantService service;
    private final EntityChangeLogService entityChangeLogService;
    private final CountryService countries;
    private final StateService states;
    private final CityService cities;

    public WebTenantController(TenantService service, EntityChangeLogService entityChangeLogService, CountryService countries, StateService states, CityService cities) {
        this.service = service;
        this.entityChangeLogService = entityChangeLogService;
        this.countries = countries;
        this.states = states;
        this.cities = cities;
    }
    @GetMapping("/web/tenants")
    public String list(@RequestParam(required = false) String tenantName,
                       @RequestParam(required = false) String tenantCode,
                       @RequestParam(required = false) String contactPhone,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                       @RequestParam(required = false) TenantStatus status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model m) {
        m.addAttribute("page", service.getTenantsPage(tenantName, tenantCode, contactPhone, fromDate, toDate, status, page, size));
        m.addAttribute("tenantNames", service.getTenantNames());
        m.addAttribute("statuses", TenantStatus.values());
        m.addAttribute("selectedTenantName", tenantName);
        m.addAttribute("tenantCode", tenantCode);
        m.addAttribute("contactPhone", contactPhone);
        m.addAttribute("fromDate", fromDate);
        m.addAttribute("toDate", toDate);
        m.addAttribute("selectedStatus", status);
        var filters = WebPagination.filters();
        filters.put("tenantName", tenantName);
        filters.put("tenantCode", tenantCode);
        filters.put("contactPhone", contactPhone);
        filters.put("fromDate", fromDate);
        filters.put("toDate", toDate);
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
    public String update(@PathVariable Long id,
                         @ModelAttribute("tenantRequest") TenantUpdateRequest r,
                         @RequestParam(required = false) String remarks,
                         RedirectAttributes a) {
        try {
            service.updateTenant(id, r, remarks);
            a.addFlashAttribute("success", "Tenant updated successfully.");
        } catch (Exception e) {
            a.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tenants";
    }
    @GetMapping("/web/tenants/{id}")
    public String view(@PathVariable Long id, Model m, RedirectAttributes a) {
        try {
            TenantDTO tenant = service.getTenantById(id);
            m.addAttribute("tenant", tenant);
            m.addAttribute("changeLogs", entityChangeLogService.getRecentChanges(TENANT_ENTITY, tenant.getId()));
            return "tenant-detail";
        } catch (Exception e) {
            a.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/tenants";
        }
    }
    @PostMapping("/web/tenants/{id}/status")
    public String status(@PathVariable Long id,
                         @RequestParam TenantStatus status,
                         @RequestParam(required = false) String remarks,
                         RedirectAttributes a) {
        service.changeTenantStatus(id, status, remarks);
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
