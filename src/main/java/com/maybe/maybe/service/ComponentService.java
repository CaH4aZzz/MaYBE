package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.repository.ComponentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentService {
    private ComponentRepository componentRepository;
    private ModelMapper modelMapper;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ComponentDTO> findAllComponent() {
        List<Component> components = componentRepository.findAll();
        List<ComponentDTO> componentDTOs = new ArrayList<>();
        modelMapper.map(components, componentDTOs);
        return componentDTOs;
    }

    public ComponentDTO save(ComponentDTO componentDTO) {
        Component component = modelMapper.map(componentDTO, Component.class);
        component.setMeasure(Measure.valueOfId(componentDTO.getMeasureId()));
        Component savedComponent = componentRepository.save(component);
        return modelMapper.map(savedComponent, ComponentDTO.class);
    }

    public ComponentDTO findById(Long id) {
        Component component = componentRepository.findById(id).orElse(null);
        return modelMapper.map(component, ComponentDTO.class);
    }
}
