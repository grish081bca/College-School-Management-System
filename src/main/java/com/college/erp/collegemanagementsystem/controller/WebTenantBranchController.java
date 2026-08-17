package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.TenantDTO;
import com.college.erp.collegemanagementsystem.dto.request.TenantCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.TenantUpdateRequest;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.service.CityService;
import com.college.erp.collegemanagementsystem.service.CountryService;
import com.college.erp.collegemanagementsystem.service.EntityChangeLogService;
import com.college.erp.collegemanagementsystem.service.StateService;
import com.college.erp.collegemanagementsystem.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class WebTenantBranchController {
    private static final String TENANT_ENTITY = "Tenant";

    private final TenantService service;
    private final EntityChangeLogService entityChangeLogService;
    private final CountryService countries;
    private final StateService states;
    private final CityService cities;

    public WebTenantBranchController(TenantService service,
                                     EntityChangeLogService entityChangeLogService,
                                     CountryService countries,
                                     StateService states,
                                     CityService cities) {
        this.service = service;
        this.entityChangeLogService = entityChangeLogService;
        this.countries = countries;
        this.states = states;
        this.cities = cities;
    }

    @GetMapping("/web/tenant-branches")
    public String list(@RequestParam(required = false, name = "q") String search,
                       @RequestParam(required = false) Long parentTenantId,
                       @RequestParam(required = false) String tenantName,
                       @RequestParam(required = false) String tenantCode,
                       @RequestParam(required = false) String contactPhone,
                       @RequestParam(required = false) String country,
                       @RequestParam(required = false) String state,
                       @RequestParam(required = false) String city,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                       @RequestParam(required = false) TenantStatus status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model model) {
        model.addAttribute("page", service.getTenantBranchesPage(search, parentTenantId, tenantName, tenantCode, contactPhone, country, state, city, fromDate, toDate, status, page, size));
        model.addAttribute("mainTenants", service.getHeadTenants());
        model.addAttribute("tenantNames", service.getTenantBranchNames());
        model.addAttribute("statuses", TenantStatus.values());
        model.addAttribute("search", search);
        model.addAttribute("parentTenantId", parentTenantId);
        model.addAttribute("selectedTenantName", tenantName);
        model.addAttribute("tenantCode", tenantCode);
        model.addAttribute("contactPhone", contactPhone);
        model.addAttribute("country", country);
        model.addAttribute("state", state);
        model.addAttribute("city", city);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("selectedStatus", status);
        var filters = WebPagination.filters();
        filters.put("q", search);
        filters.put("parentTenantId", parentTenantId);
        filters.put("tenantName", tenantName);
        filters.put("tenantCode", tenantCode);
        filters.put("contactPhone", contactPhone);
        filters.put("country", country);
        filters.put("state", state);
        filters.put("city", city);
        filters.put("fromDate", fromDate);
        filters.put("toDate", toDate);
        filters.put("status", status);
        WebPagination.add(model, "/web/tenant-branches", size, filters);
        return "tenant-branches";
    }

    @GetMapping("/web/tenant-branches/add")
    public String addForm(Model model) {
        addFormModel(model);
        return "tenant-branch-add";
    }

    @PostMapping("/web/tenant-branches")
    public String create(@Valid @ModelAttribute("tenantRequest") TenantCreateRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            addFormModel(model);
            return "tenant-branch-add";
        }
        try {
            service.createTenantBranch(request);
            attributes.addFlashAttribute("success", "Tenant branch created successfully.");
            return "redirect:/web/tenant-branches";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            addFormModel(model);
            return "tenant-branch-add";
        }
    }

    @GetMapping("/web/tenant-branches/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            TenantDTO tenant = requireBranch(id);
            addFormModel(model);
            model.addAttribute("tenantRequest", toUpdateRequest(tenant));
            model.addAttribute("tenant", tenant);
            model.addAttribute("tenantId", id);
            model.addAttribute("isEdit", true);
            return "tenant-branch-add";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/tenant-branches";
        }
    }

    @PostMapping("/web/tenant-branches/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("tenantRequest") TenantUpdateRequest request,
                         @RequestParam(required = false) String remarks,
                         RedirectAttributes attributes) {
        try {
            requireBranch(id);
            service.updateTenant(id, request, remarks);
            attributes.addFlashAttribute("success", "Tenant branch updated successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tenant-branches";
    }

    @GetMapping("/web/tenant-branches/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            TenantDTO tenant = requireBranch(id);
            model.addAttribute("tenant", tenant);
            model.addAttribute("changeLogs", entityChangeLogService.getRecentChanges(TENANT_ENTITY, tenant.getId()));
            return "tenant-branch-detail";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/tenant-branches";
        }
    }

    @PostMapping("/web/tenant-branches/{id}/status")
    public String status(@PathVariable Long id,
                         @RequestParam TenantStatus status,
                         @RequestParam(required = false) String remarks,
                         RedirectAttributes attributes) {
        try {
            requireBranch(id);
            service.changeTenantStatus(id, status, remarks);
            attributes.addFlashAttribute("success", "Tenant branch status updated successfully.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tenant-branches";
    }

    private void addFormModel(Model model) {
        model.addAttribute("mainTenants", service.getHeadTenants());
        model.addAttribute("countries", countries.getAllCountries());
        model.addAttribute("states", states.getAllStates());
        model.addAttribute("cities", cities.getAllCities());
        model.addAttribute("statuses", TenantStatus.values());
        if (!model.containsAttribute("tenantRequest")) {
            model.addAttribute("tenantRequest", new TenantCreateRequest());
        }
        if (!model.containsAttribute("isEdit")) {
            model.addAttribute("isEdit", false);
        }
    }

    private TenantDTO requireBranch(Long id) {
        TenantDTO tenant = service.getTenantById(id);
        if (!"BRANCH".equals(tenant.getTenantType())) {
            throw new IllegalArgumentException("Tenant branch not found.");
        }
        return tenant;
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
