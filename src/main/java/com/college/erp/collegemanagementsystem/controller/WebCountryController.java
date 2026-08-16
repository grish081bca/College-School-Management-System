package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.CountryCreateRequest;
import com.college.erp.collegemanagementsystem.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author grish
 *
 */
@Controller
public class WebCountryController {
    private final CountryService service;

    public WebCountryController(CountryService service) {
        this.service = service;
    }
    @GetMapping("/web/countries")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model m) {
        m.addAttribute("page", service.getCountriesPage(q, page, size));
        m.addAttribute("q", q);
        var filters = WebPagination.filters();
        filters.put("q", q);
        WebPagination.add(m, "/web/countries", size, filters);
        return "countries";
    }
    @GetMapping("/web/countries/add")
    public String add(Model m) {
        m.addAttribute("countryRequest", new CountryCreateRequest());
        return "country-add";
    }
    @PostMapping("/web/countries")
    public String create(@Valid @ModelAttribute("countryRequest") CountryCreateRequest r, BindingResult b, RedirectAttributes a) {
        if (b.hasErrors()) return "country-add";
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
