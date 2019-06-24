package com.maybe.maybe.controller;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.service.ComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public ResponseEntity<Page<Component>> getAllComponents(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(componentService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Component> addComponent(ComponentDTO componentDTO) {
        return new ResponseEntity<>(componentService.saveDTO(componentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<Component> getComponent(@PathVariable Long componentId) {
        return new ResponseEntity<>(componentService.findById(componentId), HttpStatus.OK);
    }

    @PutMapping("/{componentId}")
    public ResponseEntity<Component> saveComponent(@PathVariable Long componentId, ComponentDTO componentDTO) {
        return new ResponseEntity<>(componentService.saveDTO(componentId, componentDTO), HttpStatus.OK);
    }
}
