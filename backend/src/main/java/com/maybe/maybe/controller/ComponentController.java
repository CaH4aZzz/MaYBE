package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.service.ComponentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @Statistic
    @GetMapping
    public Page<Component> getAllComponents(@PageableDefault Pageable pageable) {
        return componentService.findAll(pageable);
    }

    @Statistic
    @PostMapping
    public ResponseEntity<Component> addComponent(@Valid @RequestBody ComponentDTO componentDTO) {
        return new ResponseEntity<>(componentService.createFromDTO(componentDTO), HttpStatus.CREATED);
    }

    @Statistic
    @GetMapping("/{componentId}")
    public Component getComponent(@PathVariable Long componentId) {
        return componentService.findById(componentId);
    }

    @Statistic
    @PutMapping("/{componentId}")
    public Component saveComponent(@PathVariable Long componentId, @Valid @RequestBody ComponentDTO componentDTO) {
        return componentService.updateFromDTO(componentId, componentDTO);
    }
}
