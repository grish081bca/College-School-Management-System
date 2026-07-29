package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.dto.UserTemplateDTO;
import com.college.erp.collegemanagementsystem.service.UserTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-templates")
public class UserTemplateController {

    private final UserTemplateService userTemplateService;

    public UserTemplateController(UserTemplateService userTemplateService) {
        this.userTemplateService = userTemplateService;
    }

    @PostMapping
    public ResponseEntity<RestResponseDTO> assign(@RequestBody UserTemplateDTO dto) {
        return ResponseEntity.ok(RestResponseDTO.success("User template assigned successfully", userTemplateService.assignUserTemplate(dto)));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> list() {
        return ResponseEntity.ok(RestResponseDTO.success("User templates found successfully", userTemplateService.findAll()));
    }
}
