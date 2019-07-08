package com.maybe.maybe.repository;

import com.maybe.maybe.entity.*;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvoiceItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    private Invoice invoice;
    private InvoiceItem invoiceItem1;
    private InvoiceItem invoiceItem2;

    @Before
    public void setUp() throws Exception {
        // given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Ivanov");
        employee.setUserRole(UserRole.USER);
        employee.setLogin("ivanov");
        employee.setPassword("123");
        entityManager.persist(employee);

        Component component = new Component();
        component.setId(1L);
        component.setName("Component");
        component.setInvoiceItems(new HashSet<>());
        component.setComponentProduct(new HashSet<>());
        component.setMeasure(Measure.PIESES);
        component.setQuantity(BigDecimal.valueOf(1));
        component.setTotal(BigDecimal.valueOf(1));
        entityManager.persist(component);

        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setDateCreated(LocalDateTime.now());
        invoice.setName("001");
        invoice.setInvoiceType(InvoiceType.INCOME);
        invoice.setEmployee(employee);
        entityManager.persist(invoice);

        invoiceItem1 = new InvoiceItem();
        invoiceItem1.setId(5L);
        invoiceItem1.setInvoice(invoice);
        invoiceItem1.setQuantity(BigDecimal.valueOf(1));
        invoiceItem1.setPrice(BigDecimal.valueOf(10));
        invoiceItem1.setComponent(component);
        entityManager.persist(invoiceItem1);

        invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(7L);
        invoiceItem2.setInvoice(invoice);
        invoiceItem2.setQuantity(BigDecimal.valueOf(2));
        invoiceItem2.setPrice(BigDecimal.valueOf(20));
        invoiceItem2.setComponent(component);
        entityManager.persist(invoiceItem2);

        entityManager.flush();
    }

    @Test
    public void when_findAllByInvoice_Id_thenReturnNotEmpty() {
        // when
        Page<InvoiceItem> found = invoiceItemRepository.findAllByInvoice_Id(invoice.getId(),
                PageRequest.of(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "id")));

        // then
        assertEquals(2, found.getTotalElements());
        assertEquals(invoiceItem1.getId(), found.getContent().get(0).getId());
        assertEquals(invoiceItem2.getId(), found.getContent().get(1).getId());
    }

    @Test
    public void when_findAllByInvoice_Id_thenReturnEmpty() {
        // when
        final Long anotherId = 10L;
        Page<InvoiceItem> found = invoiceItemRepository.findAllByInvoice_Id(anotherId,
                PageRequest.of(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "id")));

        // then
        assertEquals(0, found.getContent().size());
    }
}