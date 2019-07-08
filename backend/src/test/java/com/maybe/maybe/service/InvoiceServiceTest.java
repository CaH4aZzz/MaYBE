package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InvoiceServiceTest {

    @InjectMocks
    InvoiceService manager;

    @Mock
    InvoiceRepository invoiceRepository;

    @Mock
    EmployeeService employeeService;

    private Employee employee;
    private Invoice invoice1;
    private Invoice invoice2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Ivanov");
        employee.setUserRole(UserRole.USER);
        employee.setLogin("ivanov");
        employee.setPassword("123");

        invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setDateCreated(LocalDateTime
                .of(2019, 6, 22, 14, 0));
        invoice1.setName("001");
        invoice1.setInvoiceType(InvoiceType.INCOME);
        invoice1.setEmployee(employee);

        invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setDateCreated(
                LocalDateTime.of(2019, 6, 23, 15, 0));
        invoice2.setName("002");
        invoice2.setInvoiceType(InvoiceType.INCOME);
        invoice2.setEmployee(employee);
    }

    @Test
    public void findByIdExists() {
        // given
        Long id = 1L;

        when(invoiceRepository.findById(id))
                .thenReturn(Optional.of(invoice1));

        // when
        Invoice result = manager.findById(id);

        // then
        assertEquals(invoice1, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEmpty() {
        // given
        Long id = 3L;

        when(invoiceRepository.findById(id))
                .thenReturn(Optional.empty());

        // when
        manager.findById(id);
    }

    @Test
    public void delete() {
        // when
        manager.delete(invoice1);

        // then
        verify(invoiceRepository, times(1)).delete(invoice1);
    }

    @Test
    public void findAllByPeriod() {
        // given
        LocalDateTime dateFrom = LocalDateTime.of(2019, 6, 22, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(2019, 6, 30, 0, 0);
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "id"));

        Page<Invoice> expected12 = new PageImpl<>(Arrays.asList(invoice1, invoice2));
        Page<Invoice> expected2 = new PageImpl<>(Collections.singletonList(invoice2));

        when(invoiceRepository.findAllByDateCreatedIsBetween(dateFrom, dateTo, pageable))
                .thenReturn(expected2);
        when(invoiceRepository.findAllByDateCreatedIsBefore(dateTo, pageable))
                .thenReturn(expected12);
        when(invoiceRepository.findAllByDateCreatedIsAfter(dateFrom, pageable))
                .thenReturn(expected2);
        when(invoiceRepository.findAll(pageable))
                .thenReturn(expected12);

        // when
        Page<Invoice> resultFromTo = manager.findAllByPeriod(dateFrom, dateTo, pageable);
        Page<Invoice> resultFrom = manager.findAllByPeriod(dateFrom, null, pageable);
        Page<Invoice> resultTo = manager.findAllByPeriod(null, dateTo, pageable);
        Page<Invoice> result = manager.findAllByPeriod(null, null, pageable);

        // then
        assertEquals(expected2, resultFromTo);
        assertEquals(expected2, resultFrom);
        assertEquals(expected12, resultTo);
        assertEquals(expected12, result);
    }

    @Test
    public void createFromDTO() {
        // given
        Long employeeId = employee.getId();
        Integer invoiceTypeId = 1;

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setName("005");
        invoiceDTO.setInvoiceTypeId(invoiceTypeId);
        invoiceDTO.setEmployeeId(employeeId);

        Invoice invoice = new Invoice();

        when(employeeService.findById(employeeId))
                .thenReturn(employee);

        // when
        manager.createFromDTO(invoiceDTO);

        // then
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    public void updateFromDTO() {
        // given
        Long employeeId = employee.getId();
        Integer invoiceTypeId = 1;

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setName("005");
        invoiceDTO.setInvoiceTypeId(invoiceTypeId);
        invoiceDTO.setEmployeeId(employeeId);

        when(employeeService.findById(employeeId))
                .thenReturn(employee);

        // when
        manager.updateFromDTO(invoice1, invoiceDTO);

        // then
        verify(invoiceRepository, times(1)).save(invoice1);
    }
}