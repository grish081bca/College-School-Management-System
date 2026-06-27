package com.college.erp.collegemanagementsystem.controller;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.CountryDTO;
import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.request.CountryCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.CountryUpdateRequest;
import com.college.erp.collegemanagementsystem.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author grish
 */

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestResponseDTO> createCountry(@Valid @RequestBody CountryCreateRequest request) {
        CountryDTO response = countryService.createCountry(request);
        return new ResponseEntity<>(RestResponseDTO.success("Country Created Successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponseDTO> updateCountry(@RequestParam("id") Long id, @Valid @RequestBody CountryUpdateRequest request) {
        CountryDTO response = countryService.updateCountry(id, request);
        return ResponseEntity.ok(RestResponseDTO.success("Country Updated Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseDTO> getCountryById(@PathVariable Long id) {
        CountryDTO response = countryService.getCountryById(id);
        return ResponseEntity.ok(RestResponseDTO.success("Country Found Successfully", response));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> getAllCountries() {
        List<CountryDTO> response = countryService.getAllCountries();
        return ResponseEntity.ok(RestResponseDTO.success("Countries Found Successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDTO> deleteCountry(@PathVariable Long id) {
        CountryDTO response = countryService.deleteCountry(id);
        return ResponseEntity.ok(RestResponseDTO.success("Country Deleted Successfully", response));
    }
}
