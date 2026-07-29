package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebDashboardController {
    private final TenantService tenants;
    private final CountryService countries;
    private final StateService states;
    private final CityService cities;

    public WebDashboardController(TenantService tenants, CountryService countries, StateService states, CityService cities) {
        this.tenants = tenants;
        this.countries = countries;
        this.states = states;
        this.cities = cities;
    }

    @GetMapping("/web/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tenantCount", tenants.getAllTenants().size());
        model.addAttribute("countryCount", countries.getAllCountries().size());
        model.addAttribute("stateCount", states.getAllStates().size());
        model.addAttribute("cityCount", cities.getAllCities().size());
        return "dashboard";
    }
}
