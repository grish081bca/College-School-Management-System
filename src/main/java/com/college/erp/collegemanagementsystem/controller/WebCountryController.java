package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.CountryCreateRequest;
import com.college.erp.collegemanagementsystem.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebCountryController {
    private final CountryService service;

    public WebCountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping("/web/countries")
    public String list(Model m) {
        m.addAttribute("countries", service.getAllCountries());
        m.addAttribute("countryRequest", new CountryCreateRequest());
        return "countries";
    }

    @PostMapping("/web/countries")
    public String create(@Valid @ModelAttribute("countryRequest") CountryCreateRequest r, BindingResult b, RedirectAttributes a) {
        if (b.hasErrors()) return "countries";
        service.createCountry(r);
        a.addFlashAttribute("success", "Country created successfully.");
        return "redirect:/web/countries";
    }

    @PostMapping("/web/countries/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes a) {
        service.deleteCountry(id);
        a.addFlashAttribute("success", "Country deleted successfully.");
        return "redirect:/web/countries";
    }
}
