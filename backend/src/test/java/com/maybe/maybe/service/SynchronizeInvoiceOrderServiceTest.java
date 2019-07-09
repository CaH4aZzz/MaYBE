package com.maybe.maybe.service;

import com.maybe.maybe.entity.*;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.InvoiceItemRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnitParamsRunner.class)
public class SynchronizeInvoiceOrderServiceTest {

    @InjectMocks
    private SynchronizeInvoiceOrderService synchronizeInvoiceOrderService;

    @Mock
    private InvoiceItemRepository invoiceItemRepository;

    @Before
    public void initMock(){
        initMocks(this);
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void orderChanged(Order order) {
        ArgumentCaptor<InvoiceItem> capture = ArgumentCaptor.forClass(InvoiceItem.class);

        synchronizeInvoiceOrderService.orderChanged(order);
        verify(invoiceItemRepository).save(capture.capture());
        List<InvoiceItem> invoiceItemList = capture.getAllValues();

        assertEquals(BigDecimal.valueOf(100000,5),invoiceItemList.get(0).getPrice());
        assertEquals(BigDecimal.valueOf(10000),invoiceItemList.get(0).getQuantity());
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void orderDeleted(Order order) {
        synchronizeInvoiceOrderService.orderDeleted(order);
        Set<InvoiceItem> invoiceItems = order.getInvoice().getInvoiceItems();

        assertEquals(new BigDecimal(0),invoiceItems.iterator().next().getQuantity());
    }

    private Object[] parametersToTest(){
        Desk desk = new Desk();
        desk.setName("Desk1");
        desk.setDeskState(DeskState.AVAILABLE);
        desk.setId(1L);

        Employee employee = new Employee();
        employee.setName("employee1");
        employee.setLogin("login1");
        employee.setPassword("");
        employee.setUserRole(UserRole.USER);
        employee.setId(1L);

        Component component = new Component();
        component.setName("component1");
        component.setMeasure(Measure.GRAM);
        component.setQuantity(new BigDecimal(100));
        component.setTotal(new BigDecimal(100));
        component.setId(1L);

        Invoice invoice = new Invoice();
        invoice.setName("invoice1");
        invoice.setEmployee(employee);
        invoice.setDateCreated(LocalDateTime.now());
        invoice.setInvoiceType(InvoiceType.ORDER);
        invoice.setId(1L);

        Set<InvoiceItem> invoiceItems = new HashSet<>();
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setComponent(component);
        invoiceItem.setInvoice(invoice);
        invoiceItem.setQuantity(new BigDecimal(100));
        invoiceItem.setPrice(new BigDecimal(100));
        invoiceItems.add(invoiceItem);
        invoice.setInvoiceItems(invoiceItems);
        invoiceItem.setId(1L);

        Product product = new Product();
        product.setPrice(new BigDecimal(100));
        product.setName("product");
        product.setId(1L);

        Set<ComponentProduct> componentProducts = new HashSet<>();
        ComponentProduct componentProduct = new ComponentProduct();
        componentProduct.setComponent(component);
        componentProduct.setProduct(product);
        componentProduct.setQuantity(new BigDecimal(100));
        componentProducts.add(componentProduct);
        product.setComponentProducts(componentProducts);

        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(new BigDecimal(100));
        orderItem.setProduct(product);
        orderItem.setQuantity(new BigDecimal(100));
        orderItem.setOrder(new Order());
        orderItems.add(orderItem);
        orderItem.setId(1L);

        Order order = new Order();
        order.setTotal(new BigDecimal(100));
        order.setDesk(desk);
        order.setEmployee(employee);
        order.setDateClosed(LocalDateTime.now());
        order.setDateCreated(LocalDateTime.now());
        order.setInvoice(invoice);
        order.setOrderItems(orderItems);
        order.setId(1L);

        return new Object[]{order};
    }
}