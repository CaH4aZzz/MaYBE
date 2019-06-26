package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceItemDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.repository.InvoiceItemRepository;
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

public class InvoiceItemServiceTest {

    @InjectMocks
    InvoiceItemService manager;

    @Mock
    InvoiceItemRepository invoiceItemRepository;

    @Mock
    InvoiceService invoiceService;

    @Mock
    ComponentService componentService;

    private Invoice invoice;
    private InvoiceItem invoiceItem1;
    private InvoiceItem invoiceItem2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // given
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setDateCreated(LocalDateTime.now());
        invoice.setName("001");
        invoice.setInvoiceType(InvoiceType.INCOME);

        invoiceItem1 = new InvoiceItem();
        invoiceItem1.setId(5L);
        invoiceItem1.setInvoice(invoice);

        invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(7L);
        invoiceItem2.setInvoice(invoice);
    }

    @Test
    public void findByIdExists() {
        // given
        Long id = invoiceItem1.getId();

        when(invoiceItemRepository.findById(id))
                .thenReturn(Optional.of(invoiceItem1));

        // when
        InvoiceItem result = manager.findById(id);

        // then
        assertEquals(invoiceItem1, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEmpty() {
        // given
        Long id = 3L;

        when(invoiceItemRepository.findById(id))
                .thenReturn(Optional.empty());

        // when
        manager.findById(id);
    }

    @Test
    public void deleteById() {
        // given
        Long id = invoiceItem1.getId();

        // when
        manager.deleteById(id);

        // then
        verify(invoiceItemRepository, times(1)).deleteById(id);
    }

    @Test
    public void findAllByInvoice_Id() {
        // given
        Long existedInvoiceId = invoice.getId();
        Long notExistedInvoiceId = 12L;
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "id"));

        Page<InvoiceItem> expected12 = new PageImpl<>(Arrays.asList(invoiceItem1, invoiceItem2));
        Page<InvoiceItem> expectedEmpty = new PageImpl<>(Collections.emptyList());

        when(invoiceItemRepository.findAllByInvoice_Id(existedInvoiceId, pageable))
                .thenReturn(expected12);
        when(invoiceItemRepository.findAllByInvoice_Id(notExistedInvoiceId, pageable))
                .thenReturn(expectedEmpty);

        // when
        Page<InvoiceItem> result12 = manager.findAllByInvoice_Id(existedInvoiceId, pageable);
        Page<InvoiceItem> resultEmpty = manager.findAllByInvoice_Id(notExistedInvoiceId, pageable);

        // then
        assertEquals(expected12, result12);
        assertEquals(expectedEmpty, resultEmpty);
    }

    @Test
    public void createFromDTO() {
        // given
        Long invoiceId = invoice.getId();

        Component component = new Component();
        component.setId(1L);
        Long componentId = component.getId();

        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();
        invoiceItemDTO.setComponentId(componentId);

        InvoiceItem invoiceItem = new InvoiceItem();

        when(invoiceService.findById(invoiceId))
                .thenReturn(invoice);
        when(componentService.findById(componentId))
                .thenReturn(component);

        // when
        manager.createFromDTO(invoiceItemDTO, invoiceId);

        // then
        verify(invoiceItemRepository, times(1)).save(invoiceItem);
    }

    @Test
    public void updateFromDTO() {
        // given
        Long invoiceId = invoice.getId();

        Component component = new Component();
        component.setId(1L);
        Long componentId = component.getId();

        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();
        invoiceItemDTO.setComponentId(componentId);

        when(invoiceService.findById(invoiceId))
                .thenReturn(invoice);
        when(componentService.findById(componentId))
                .thenReturn(component);

        // when
        manager.updateFromDTO(invoiceItem1, invoiceItemDTO);

        // then
        verify(invoiceItemRepository, times(1)).save(invoiceItem1);
    }
}