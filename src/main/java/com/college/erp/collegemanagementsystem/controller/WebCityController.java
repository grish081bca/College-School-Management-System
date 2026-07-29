package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.CityCreateRequest;
import com.college.erp.collegemanagementsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebCityController {
    private final CityService service;
    private final StateService states;

    public WebCityController(CityService service, StateService states) {
        this.service = service;
        this.states = states;
    }

    @GetMapping("/web/cities")
    public String list(Model m) {
        add(m);
        return "cities";
    }

    private void add(Model m) {
        m.addAttribute("cities", service.getAllCities());
        m.addAttribute("states", states.getAllStates());
        m.addAttribute("cityRequest", new CityCreateRequest());
    }

    @PostMapping("/web/cities")
    public String create(@Valid @ModelAttribute("cityRequest") CityCreateRequest r, BindingResult b, Model m, RedirectAttributes a) {
        if (b.hasErrors()) {
            add(m);
            return "cities";
        }
        service.createCity(r);
        a.addFlashAttribute("success", "City created successfully.");
        return "redirect:/web/cities";
    }

    @PostMapping("/web/cities/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes a) {
        service.deleteCity(id);
        a.addFlashAttribute("success", "City deleted successfully.");
        return "redirect:/web/cities";
    }
}
