package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.exception.NotEnoughComponentException;
import com.maybe.maybe.repository.ComponentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComponentServiceTest {

    @InjectMocks
    @Spy
    private ComponentService componentService;
    @Mock
    private ComponentRepository componentRepository;

    @Test
    public void findAllTest() {
        //GIVEN
        List<Component> expectedListComponents = new ArrayList<>();
        expectedListComponents.add(new Component());
        expectedListComponents.add(new Component());
        expectedListComponents.add(new Component());
        Page<Component> expectedComponents = new PageImpl<>(expectedListComponents);
        Pageable pageable = PageRequest.of(0, 10);
        when(componentRepository.findAll(pageable)).thenReturn(expectedComponents);

        //WHEN
        Page<Component> components = componentService.findAll(pageable);

        //THEN
        assertEquals(expectedComponents, components);
    }

    @Test
    public void createFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Component expectedComponent = new Component();
        expectedComponent.setName(componentDTO.getName());
        expectedComponent.setMeasure(componentDTO.getMeasure());
        when(componentRepository.save(any(Component.class))).thenReturn(expectedComponent);
        ArgumentCaptor<Component> argumentCaptor = ArgumentCaptor.forClass(Component.class);

        //WHEN
        Component component = componentService.createFromDTO(componentDTO);
        verify(componentRepository).save(argumentCaptor.capture());

        //THEN
        assertEquals(expectedComponent, component);
        assertEquals(componentDTO.getName(), argumentCaptor.getValue().getName());
        assertEquals(componentDTO.getMeasure(), argumentCaptor.getValue().getMeasure());
    }

    @Test
    public void updateExistentComponentFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Long componentId = 1L;
        Component expectedComponent = new Component();
        expectedComponent.setId(componentId);
        when(componentRepository.findById(componentId)).thenReturn(Optional.of(expectedComponent));
        when(componentRepository.save(expectedComponent)).thenReturn(expectedComponent);

        ArgumentCaptor<Component> argumentCaptor = ArgumentCaptor.forClass(Component.class);

        //WHEN
        Component component = componentService.updateFromDTO(componentId, componentDTO);
        verify(componentRepository).save(argumentCaptor.capture());

        //THEN
        assertEquals(expectedComponent, component);
        assertEquals(componentDTO.getName(), argumentCaptor.getValue().getName());
        assertEquals(componentDTO.getMeasure(), argumentCaptor.getValue().getMeasure());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateNonexistentComponentFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Long componentId = 1L;
        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        //WHEN
        componentService.updateFromDTO(componentId, componentDTO);
    }

    @Test
    public void findExistentComponentByIdTest() {
        //GIVEN
        Long componentId = 1L;
        Component expectedComponent = new Component();
        expectedComponent.setId(componentId);
        when(componentRepository.findById(componentId)).thenReturn(Optional.of(expectedComponent));

        //WHEN
        Component component = componentService.findById(componentId);

        //THEN
        assertEquals(expectedComponent, component);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findNonexistentComponentByIdTest() {
        //GIVEN
        Long componentId = 1L;
        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        //WHEN
        componentService.findById(componentId);
    }

    @Test
    public void increaseComponentBalanceTest() {
        // given
        Component component = new Component();
        component.setId(1L);
        component.setQuantity(BigDecimal.valueOf(10));
        component.setTotal(BigDecimal.valueOf(30));

        Long componentId = component.getId();
        BigDecimal quantity = BigDecimal.valueOf(5);
        BigDecimal total = BigDecimal.valueOf(40);

        Mockito.doReturn(component).when(componentService).findById(componentId);
        when(componentRepository.save(component))
                .thenReturn(component);

        // when
        Component actualComponent = componentService.increaseComponentBalance(componentId,
                quantity, total);

        // then
        assertEquals(BigDecimal.valueOf(15), actualComponent.getQuantity());
        assertEquals(BigDecimal.valueOf(70), actualComponent.getTotal());
        verify(componentRepository, times(1)).save(actualComponent);
    }

    @Test(expected = NotEnoughComponentException.class)
    public void increaseComponentBalanceWhenNegativeQuantityTest() {
        // given
        Component component = new Component();
        component.setId(1L);
        component.setQuantity(BigDecimal.valueOf(10));
        component.setTotal(BigDecimal.valueOf(30));

        Long componentId = component.getId();
        BigDecimal quantity = BigDecimal.valueOf(-11);
        BigDecimal total = BigDecimal.valueOf(-40);

        Mockito.doReturn(component).when(componentService).findById(componentId);

        // when
        componentService.increaseComponentBalance(componentId,
                quantity, total);
    }

    @Test
    public void decreaseComponentBalance() {
        Component component = new Component();
        component.setQuantity(BigDecimal.valueOf(15));
        component.setTotal(BigDecimal.valueOf(300, 2));
        component.setId(1L);
        BigDecimal subtrahendQuantity = BigDecimal.valueOf(5);
        BigDecimal subtrahendTotal = BigDecimal.valueOf(100, 2);
        BigDecimal expectedQuantity = component.getQuantity().subtract(subtrahendQuantity);
        BigDecimal expectedTotal = component.getTotal().subtract(subtrahendTotal);
        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));
        when(componentRepository.save(component)).thenReturn(component);

        component.setQuantity(componentService.decreaseComponentBalance(1L, subtrahendQuantity).getQuantity());

        assertEquals(expectedQuantity, component.getQuantity());
        assertEquals(expectedTotal, component.getTotal());
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenComponentDoesNotExist_thenThrownException() {
        when(componentRepository.findById(1L)).thenReturn(Optional.empty());

        componentService.decreaseComponentBalance(1L, BigDecimal.valueOf(5));
    }

    @Test(expected = NotEnoughComponentException.class)
    public void whenComponentQuantityLessThenSubtrahendQuantity_thenThrownException() {
        Component component = new Component();
        component.setQuantity(BigDecimal.valueOf(5));
        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));

        componentService.decreaseComponentBalance(1L, BigDecimal.valueOf(6));
    }
}