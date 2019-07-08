package com.maybe.maybe.repository;

import com.maybe.maybe.entity.*;
import com.maybe.maybe.entity.enums.InvoiceType;
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

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvoiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvoiceRepository invoiceRepository;

    private Invoice invoice1;
    private Invoice invoice2;
    private Invoice invoice3;
    final private PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE,
            new Sort(Sort.Direction.ASC, "id"));

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

        invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setDateCreated(LocalDateTime
                .of(2019, 6, 22, 14, 0));
        invoice1.setName("001");
        invoice1.setInvoiceType(InvoiceType.INCOME);
        invoice1.setEmployee(employee);
        entityManager.persist(invoice1);

        invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setDateCreated(
                LocalDateTime.of(2019, 6, 23, 15, 0));
        invoice2.setName("002");
        invoice2.setInvoiceType(InvoiceType.INCOME);
        invoice2.setEmployee(employee);
        entityManager.persist(invoice2);

        invoice3 = new Invoice();
        invoice3.setId(3L);
        invoice3.setDateCreated(
                LocalDateTime.of(2019, 6, 24, 16, 0));
        invoice3.setName("003");
        invoice3.setInvoiceType(InvoiceType.INCOME);
        invoice3.setEmployee(employee);
        entityManager.persist(invoice3);

        entityManager.flush();
    }

    @Test
    public void when_findAllByDateCreatedIsAfter_thenReturnNotEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsAfter(
                LocalDateTime.of(2019, 6, 23, 0, 0),
                pageRequest);

        // then
        assertEquals(2, found.getTotalElements());
        assertEquals(invoice2.getId(), found.getContent().get(0).getId());
        assertEquals(invoice3.getId(), found.getContent().get(1).getId());
    }

    @Test
    public void when_findAllByDateCreatedIsAfter_thenReturnEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsAfter(
                LocalDateTime.of(2019, 6, 30, 0, 0),
                pageRequest);

        // then
        assertEquals(0, found.getContent().size());
    }

    @Test
    public void when_findAllByDateCreatedIsBefore_thenReturnNotEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsBefore(
                LocalDateTime.of(2019, 6, 30, 0, 0),
                pageRequest);

        // then
        assertEquals(4, found.getTotalElements());
        assertEquals(invoice1.getId(), found.getContent().get(1).getId());
        assertEquals(invoice2.getId(), found.getContent().get(2).getId());
    }

    @Test
    public void when_findAllByDateCreatedIsBefore_thenReturnEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsBefore(
                LocalDateTime.of(2019, 1, 1, 0, 0),
                pageRequest);

        // then
        assertEquals(0, found.getContent().size());
    }

    @Test
    public void when_findAllByDateCreatedIsBetween_thenReturnNotEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsBetween(
                LocalDateTime.of(2019, 6, 20, 0, 0),
                LocalDateTime.of(2019, 6, 24, 0, 0),
                pageRequest);

        // then
        assertEquals(2, found.getTotalElements());
        assertEquals(invoice1.getId(), found.getContent().get(0).getId());
        assertEquals(invoice2.getId(), found.getContent().get(1).getId());
    }

    @Test
    public void when_findAllByDateCreatedIsBetween_thenReturnEmpty() {
        // when
        Page<Invoice> found = invoiceRepository.findAllByDateCreatedIsBetween(
                LocalDateTime.of(2019, 6, 1, 0, 0),
                LocalDateTime.of(2019, 6, 5, 0, 0),
                pageRequest);

        // then
        assertEquals(0, found.getContent().size());
    }
}