package com.college.erp.collegemanagementsystem.controller;

import com.college.erp.collegemanagementsystem.dto.MenuDTO;
import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;
import com.college.erp.collegemanagementsystem.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<RestResponseDTO> save(@RequestBody MenuDTO dto) {
        return ResponseEntity.ok(RestResponseDTO.success("Menu saved successfully", menuService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDTO> update(@PathVariable Long id, @RequestBody MenuDTO dto) {
        return ResponseEntity.ok(RestResponseDTO.success("Menu updated successfully", menuService.update(id, dto)));
    }

    @GetMapping
    public ResponseEntity<RestResponseDTO> list(@RequestParam(required = false) String q,
                                                @RequestParam(required = false) MenuStatus status,
                                                @RequestParam(required = false) MenuType menuType,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(RestResponseDTO.success("Menus found successfully", menuService.findPage(q, status, menuType, page, size)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RestResponseDTO> status(@PathVariable Long id, @RequestParam MenuStatus status) {
        return ResponseEntity.ok(RestResponseDTO.success("Menu status updated successfully", menuService.changeStatus(id, status)));
    }
}
