package com.maybe.maybe.controller;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.service.ComponentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private ComponentService componentService;
    private ModelMapper modelMapper;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @GetMapping
    public List<ComponentDTO> getAllComponents() {
        return componentService.findAllComponent();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentDTO addComponent(ComponentDTO componentDTO) {
        componentDTO.setId(null);
        return componentService.save(componentDTO);
    }

    @GetMapping("/{id}")
    public ComponentDTO getComponent(@PathVariable Long id) {
        return componentService.findById(id);
    }

    @PutMapping("/{id}")
    public ComponentDTO saveComponent(@PathVariable Long id, ComponentDTO componentDTO) {
        componentDTO.setId(id);
        //TODO
//        componentDTO.setMeasure(Measure.valueOfId(componentDTO.getMeasureId()));
        return componentService.save(componentDTO);
    }
}
