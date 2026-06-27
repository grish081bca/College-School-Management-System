package com.college.erp.collegemanagementsystem.controller;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.CityDTO;
import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.request.CityCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.CityUpdateRequest;
import com.college.erp.collegemanagementsystem.service.CityService;
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
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestResponseDTO> createCity(@Valid @RequestBody CityCreateRequest request) {
        CityDTO response = cityService.createCity(request);
        return new ResponseEntity<>(RestResponseDTO.success("City Created Successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponseDTO> updateCity(@RequestParam("id") Long id, @Valid @RequestBody CityUpdateRequest request) {
        CityDTO response = cityService.updateCity(id, request);
        return ResponseEntity.ok(RestResponseDTO.success("City Updated Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseDTO> getCityById(@PathVariable Long id) {
        CityDTO response = cityService.getCityById(id);
        return ResponseEntity.ok(RestResponseDTO.success("City Found Successfully", response));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> getAllCities() {
        List<CityDTO> response = cityService.getAllCities();
        return ResponseEntity.ok(RestResponseDTO.success("Cities Found Successfully", response));
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<RestResponseDTO> getCitiesByState(@PathVariable Long stateId) {
        List<CityDTO> response = cityService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(RestResponseDTO.success("Cities Found Successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDTO> deleteCity(@PathVariable Long id) {
        CityDTO response = cityService.deleteCity(id);
        return ResponseEntity.ok(RestResponseDTO.success("City Deleted Successfully", response));
    }
}
