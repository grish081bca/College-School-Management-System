package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.StateCreateRequest;
import com.college.erp.collegemanagementsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebStateController {
    private final StateService service;
    private final CountryService countries;

    public WebStateController(StateService service, CountryService countries) {
        this.service = service;
        this.countries = countries;
    }

    @GetMapping("/web/states")
    public String list(Model m) {
        add(m);
        return "states";
    }

    private void add(Model m) {
        m.addAttribute("states", service.getAllStates());
        m.addAttribute("countries", countries.getAllCountries());
        m.addAttribute("stateRequest", new StateCreateRequest());
    }

    @PostMapping("/web/states")
    public String create(@Valid @ModelAttribute("stateRequest") StateCreateRequest r, BindingResult b, Model m, RedirectAttributes a) {
        if (b.hasErrors()) {
            add(m);
            return "states";
        }
        service.createState(r);
        a.addFlashAttribute("success", "State created successfully.");
        return "redirect:/web/states";
    }

    @PostMapping("/web/states/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes a) {
        service.deleteState(id);
        a.addFlashAttribute("success", "State deleted successfully.");
        return "redirect:/web/states";
    }
}
