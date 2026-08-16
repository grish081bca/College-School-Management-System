package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.request.CityCreateRequest;
import com.college.erp.collegemanagementsystem.service.*;
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
public class WebCityController {
    private final CityService service;
    private final StateService states;

    public WebCityController(CityService service, StateService states) {
        this.service = service;
        this.states = states;
    }
    @GetMapping("/web/cities")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) Long stateId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model m) {
        m.addAttribute("page", service.getCitiesPage(q, stateId, page, size));
        m.addAttribute("states", states.getAllStates());
        m.addAttribute("q", q);
        m.addAttribute("stateId", stateId);
        var filters = WebPagination.filters();
        filters.put("q", q);
        filters.put("stateId", stateId);
        WebPagination.add(m, "/web/cities", size, filters);
        return "cities";
    }

    private void add(Model m) {
        m.addAttribute("cities", service.getAllCities());
        m.addAttribute("states", states.getAllStates());
        m.addAttribute("cityRequest", new CityCreateRequest());
    }
    @GetMapping("/web/cities/add")
    public String addForm(Model m) {
        add(m);
        return "city-add";
    }
    @PostMapping("/web/cities")
    public String create(@Valid @ModelAttribute("cityRequest") CityCreateRequest r, BindingResult b, Model m, RedirectAttributes a) {
        if (b.hasErrors()) {
            add(m);
            return "city-add";
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
