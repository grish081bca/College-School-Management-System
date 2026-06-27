package com.college.erp.collegemanagementsystem.controller;

import java.util.List;

import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.StateDTO;
import com.college.erp.collegemanagementsystem.dto.request.StateCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.StateUpdateRequest;
import com.college.erp.collegemanagementsystem.service.StateService;
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
@RequestMapping("/api/v1/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestResponseDTO> createState(@Valid @RequestBody StateCreateRequest request) {
        StateDTO response = stateService.createState(request);
        return new ResponseEntity<>(RestResponseDTO.success("State Created Successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponseDTO> updateState(@RequestParam("id") Long id, @Valid @RequestBody StateUpdateRequest request) {
        StateDTO response = stateService.updateState(id, request);
        return ResponseEntity.ok(RestResponseDTO.success("State Updated Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseDTO> getStateById(@PathVariable Long id) {
        StateDTO response = stateService.getStateById(id);
        return ResponseEntity.ok(RestResponseDTO.success("State Found Successfully", response));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> getAllStates() {
        List<StateDTO> response = stateService.getAllStates();
        return ResponseEntity.ok(RestResponseDTO.success("States Found Successfully", response));
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<RestResponseDTO> getStatesByCountry(@PathVariable Long countryId) {
        List<StateDTO> response = stateService.getStatesByCountryId(countryId);
        return ResponseEntity.ok(RestResponseDTO.success("States Found Successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDTO> deleteState(@PathVariable Long id) {
        StateDTO response = stateService.deleteState(id);
        return ResponseEntity.ok(RestResponseDTO.success("State Deleted Successfully", response));
    }
}
