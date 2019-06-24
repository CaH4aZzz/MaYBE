package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.converter.MeasureConverter;
import com.maybe.maybe.repository.ComponentRepository;
import jdk.nashorn.internal.parser.Token;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentService {
    private ComponentRepository componentRepository;
//    private ModelMapper modelMapper;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
//        this.modelMapper = new ModelMapper();
    }

    public List<ComponentDTO> findAllComponents() {
        List<Component> components = componentRepository.findAll();
//        Type listType = new TypeToken<List<ComponentDTO>>() {}.getType();
//        return modelMapper.map(components, listType);
        return components.stream().map(c -> convertEntityToDTO(c)).collect(Collectors.toList());
    }

    private Component convertDTOtoEntity(ComponentDTO componentDTO) {
        Component component = new Component();
        component.setId(componentDTO.getId());
        component.setName(componentDTO.getName());
        Optional<Measure> optionalMeasure = MeasureConverter.getById(componentDTO.getMeasureId());
        optionalMeasure.ifPresent(component::setMeasure);
        component.setQuantity(componentDTO.getQuantity());
        component.setPrice(componentDTO.getPrice());
        return component;
    }

    private ComponentDTO convertEntityToDTO(Component component) {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setId(component.getId());
        componentDTO.setName(component.getName());
        componentDTO.setMeasureId(component.getMeasure().getId());
        componentDTO.setQuantity(component.getQuantity());
        componentDTO.setPrice(component.getPrice());
        return componentDTO;
    }

    public ComponentDTO save(ComponentDTO componentDTO) {
//        Component component = modelMapper.map(componentDTO, Component.class);
//        component.setMeasure(MeasureConverter.getById(componentDTO.getMeasureId()).orElse(null));
//        Component savedComponent = componentRepository.save(component);
//        return modelMapper.map(savedComponent, ComponentDTO.class);
        Component savedComponent = componentRepository.save(convertDTOtoEntity(componentDTO));
        return convertEntityToDTO(savedComponent);
    }

    public ComponentDTO findById(Long id) {
        Component component = componentRepository.findById(id).orElse(null);
//        return modelMapper.map(component, ComponentDTO.class);
        return convertEntityToDTO(component);
    }
}
