package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.exception.NotEnoughComponentException;
import com.maybe.maybe.repository.ComponentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

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

    public Component increaseComponentBalance(Long componentId, BigDecimal quantity,
                                              BigDecimal total) {
        Component component = findById(componentId);
        BigDecimal componentQuantity = component.getQuantity();
        BigDecimal componentTotal = component.getTotal();
        BigDecimal newQuantity = componentQuantity.add(quantity);
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughComponentException("Not enough component " + component.getName());
        }
        BigDecimal newTotal = componentTotal.add(total);
        
        component.setQuantity(newQuantity);
        component.setTotal(newTotal);
        return componentRepository.save(component);
    }

    public Component decreaseComponentBalance(Long componentId, BigDecimal quantity) {
        Optional<Component> componentFromDb = componentRepository.findById(componentId);

        if (!componentFromDb.isPresent()) {
            throw new EntityNotFoundException("Can not found component with id: " + componentId);
        }

        Component component = componentFromDb.get();
        if (component.getQuantity().compareTo(quantity) < 0) {
            throw new NotEnoughComponentException("Not enough components with name " + component.getName());
        } else if (component.getQuantity().compareTo(quantity) == 0) {
            component.setQuantity(BigDecimal.ZERO);
            component.setTotal(BigDecimal.ZERO);
        } else {
            BigDecimal price = component.getTotal().divide(component.getQuantity(),2, RoundingMode.FLOOR);
            component.setQuantity(component.getQuantity().subtract(quantity));
            component.setTotal(price.multiply(component.getQuantity()));
        }

        return componentRepository.save(component);
    }
}
