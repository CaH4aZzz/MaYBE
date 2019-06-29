package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.repository.ComponentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ComponentService {
    private ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Page<Component> findAll(Pageable pageable) {
        return componentRepository.findAll(pageable);
    }

    private Component convertDTOtoEntity(ComponentDTO componentDTO) {
        Long componentId = null;
        return convertDTOtoEntity(componentId, componentDTO);
    }

    private Component convertDTOtoEntity(Long componentId, ComponentDTO componentDTO) {
        Component component;
        if (componentId != null) {
            component = findById(componentId);
        } else {
            component = new Component();
        }
        component.setName(componentDTO.getName());
        component.setMeasure(componentDTO.getMeasure());
        return component;
    }

    public Component createFromDTO(ComponentDTO componentDTO) {
        return componentRepository.save(convertDTOtoEntity(componentDTO));
    }

    public Component updateFromDTO(Long componentId, ComponentDTO componentDTO) {
        return componentRepository.save(convertDTOtoEntity(componentId, componentDTO));
    }

    public Component findById(Long id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find component id=" + id));
    }
}
