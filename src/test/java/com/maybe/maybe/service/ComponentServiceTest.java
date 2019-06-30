package com.maybe.maybe.service;

import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.repository.ComponentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ComponentServiceTest {

    @InjectMocks
    @Spy
    ComponentService manager;

    @Mock
    ComponentRepository componentRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void increaseComponentBalance() {
        // given
        Component component = new Component();
        component.setId(1L);
        component.setQuantity(BigDecimal.valueOf(10));
        component.setTotal(BigDecimal.valueOf(30));

        Long componentId = component.getId();
        BigDecimal quantity = BigDecimal.valueOf(5);
        BigDecimal total = BigDecimal.valueOf(40);

        Mockito.doReturn(component).when(manager).findById(componentId);
        when(componentRepository.save(component))
                .thenReturn(component);


        // when
        Component actualComponent =  manager.increaseComponentBalance(componentId,
                quantity, total);

        // then
        assertEquals(BigDecimal.valueOf(15), actualComponent.getQuantity());
        assertEquals(BigDecimal.valueOf(33.33), actualComponent.getTotal());
        verify(componentRepository, times(1)).save(actualComponent);
    }
}