package com.maybe.maybe.entity;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ComponentTest {
    private Component component1;
    private Component component2;

    @Before
    public void setUp() {
        component1 = new Component();
        component1.setQuantity(BigDecimal.valueOf(4578));
        component1.setTotal(BigDecimal.valueOf(45734.22));

        component2 = new Component();
        component2.setTotal(BigDecimal.valueOf(100000));
        component2.setQuantity(BigDecimal.valueOf(300));
    }

    @Test
    public void getAveragePrice() {
        BigDecimal actual1 = component1.getAveragePrice();
        BigDecimal actual2 = component2.getAveragePrice();

        assertEquals(BigDecimal.valueOf(9.99000d).setScale(5), actual1);
        assertEquals(BigDecimal.valueOf(333.33333d).setScale(5), actual2);

    }
}