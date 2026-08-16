package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.MenuTemplateDTO;
import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.service.MenuTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author grish
 *
 */
@RestController
@RequestMapping("/api/v1/menu-templates")
public class MenuTemplateController {

    private final MenuTemplateService menuTemplateService;

    public MenuTemplateController(MenuTemplateService menuTemplateService) {
        this.menuTemplateService = menuTemplateService;
    }
    @PostMapping
    public ResponseEntity<RestResponseDTO> assign(@RequestBody MenuTemplateDTO dto) {
        return ResponseEntity.ok(RestResponseDTO.success("Menu template assigned successfully", menuTemplateService.assignMenuTemplate(dto)));
    }
    @GetMapping
    public ResponseEntity<RestResponseDTO> list(@RequestParam(required = false) String q,
                                                @RequestParam(required = false) UserType userType,
                                                @RequestParam(required = false) MenuStatus status,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(RestResponseDTO.success("Menu templates found successfully", menuTemplateService.findPage(q, userType, status, page, size)));
    }
    @GetMapping("/menus")
    public ResponseEntity<RestResponseDTO> menus(@RequestParam UserType userType) {
        return ResponseEntity.ok(RestResponseDTO.success("Menus found successfully", menuTemplateService.findMenusByUserType(userType)));
    }
}
