package com.maybe.maybe.service;

import com.maybe.maybe.entity.*;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.InvoiceItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class SynchronizeInvoiceOrderServiceTest {

    @Mock
    private ComponentService componentService;

    @Mock
    private InvoiceItemRepository invoiceItemRepository;


    @Test
    public void orderChanged() {

    }

    @Test
    public void orderDeleted() {
    }

   /* private Order parametersToTest(){
        Desk desk = new Desk();
        desk.setName("Desk1");
        desk.setDeskState(DeskState.AVAILABLE);

        Employee employee = new Employee();
        employee.setName("employee1");
        employee.setLogin("login1");
        employee.setPassword("");
        employee.setUserRole(UserRole.USER);

        *//*List<InvoiceItem> invoiceItems = new ArrayList<>();
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setComponent();
        invoiceItem.setInvoice();
        invoiceItem.setQuantity();
        invoiceItem.setPrice();*//*

        Invoice invoice = new Invoice();
        invoice.setName("invoice1");
        invoice.setEmployee(employee);
        invoice.setDateCreated(LocalDateTime.now());
        invoice.setInvoiceType(InvoiceType.ORDER);

        ComponentProduct componentProduct = new ComponentProduct();
        componentProduct.setComponent();
        componentProduct.setProduct();
        componentProduct.setQuantity();

        Product product = new Product();
        product.setComponentProducts();
        product.setPrice();
        product.setName();
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(new BigDecimal(100));
        orderItem.setQuantity(new BigDecimal(100));
        orderItem.setProduct();
        orderItem.setOrder();

        Order order = new Order();
        order.setTotal();
        order.setDesk();
        order.setEmployee();
        order.setDateClosed();
        order.setDateCreated();
        order.setInvoice();
        order.setOrderItems();
        return null;
    }*/
}